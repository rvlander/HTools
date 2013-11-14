/* $Id: mywintab.c,v 1.2 2000/02/01 05:40:21 rekimoto Exp $ */


#include "mywintab.h"
#include "wactablet.h"

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/time.h>
#include <errno.h>
#include <pthread.h>

void * sampler(void *);

const char* pszFile = "";///dev/input/event5";
const char* arg;
WACOMENGINE hEngine = NULL;
WACOMTABLET hTablet = NULL;
int nLevel = (int) WACOMLOGLEVEL_WARN;
const char* pszDeviceCls = NULL;
const char* pszDeviceType = NULL;
const char* pszLogFile = NULL;
WACOMMODEL model = {0};
int delay = 10;

 pthread_mutex_t mutex_stock;

/* from linux/input.h */
#define BITS_PER_LONG (sizeof(long) * 8)
#define NBITS(x) ((((x)-1)/BITS_PER_LONG)+1)
#define BIT(x)  (1UL<<((x)%BITS_PER_LONG))
#define LONG(x) ((x)/BITS_PER_LONG)
#define ISBITSET(x,y) ((x)[LONG(y)] & BIT(y))

 
struct timeval sto;
 
static int InitTablet(WACOMTABLET hTablet) {
    int i, nCaps, nItem, nRow = 0;
    int nMajor, nMinor, nRelease;
    char chBuf[256];
    WACOMMODEL model;
    WACOMSTATE ranges = WACOMSTATE_INIT;
    const char* pszName;
    const char* pszClass = "UNK_CLS";
    const char* pszVendor = "UNK_VNDR";
    const char* pszDevice = "UNK_DEV";
    const char* pszSub = "UNK_SUB";

    /* get model */
    model = WacomGetModel(hTablet);
    pszVendor = WacomGetVendorName(hTablet);
    pszClass = WacomGetClassName(hTablet);
    pszDevice = WacomGetDeviceName(hTablet);
    pszSub = WacomGetSubTypeName(hTablet);
    pszName = WacomGetModelName(hTablet);
    WacomGetROMVersion(hTablet, &nMajor, &nMinor, &nRelease);
    snprintf(chBuf, sizeof (chBuf), "MODEL=%s", pszName);

    snprintf(chBuf, sizeof (chBuf), "ROM=%d.%d-%d", nMajor, nMinor, nRelease);

    ++nRow;
    snprintf(chBuf, sizeof (chBuf), "CLS=%s  VNDR=%s  DEV=%s  SUB=%s",
            pszClass, pszVendor, pszDevice, pszSub);

    nRow += 2;


    nRow += 2;

    /* get event types supported, ranges, and immediate values */
    nCaps = WacomGetCapabilities(hTablet);
    WacomGetState(hTablet, &ranges);

    nItem = 0;
    /*for (i=0; i<31; ++i)
    {
            if (nCaps & (1<<i))
            {
                    gAbsState[i].bValid = 1;
                    gAbsState[i].nValue = ranges.values[i].nValue;
                    gAbsState[i].nMin = ranges.values[i].nMin;
                    gAbsState[i].nMax = ranges.values[i].nMax;
                    gAbsState[i].nRow = nRow + nItem / 2;
                    gAbsState[i].nCol = nItem % 2;
                    DisplaySerialValue(i);
                    ++nItem;
            }
    }*/
    nRow += ((nItem + 1) / 2) + 1;

    /* get key event types */
    nItem = 0;
    /*for (i=0; i<WACOMBUTTON_MAX; ++i)
    {
            gKeyState[i].bValid = 1;
            gKeyState[i].nRow = nRow + nItem / 4;
            gKeyState[i].nCol = nItem % 4;
            DisplaySerialButton(i);
            ++nItem;
    }
    nRow += ((nItem + 3) / 4) + 1;*/
    return 0;
}
long last_time = 0;

int GetFirstValid(WACOMTABLET hTablet, float *lX, float *lY, int *lB, long *lT) {
    char chOut[128];
    unsigned char uchBuf[64];
    int i, nLength, nErrors = 0;
    WACOMSTATE state = WACOMSTATE_INIT;
    long curr_time;
    int j = 0;
    int valid = 0;
    
    // printf("GFV\n"); 



    while (!valid) {
        nLength = WacomReadRaw(hTablet, uchBuf, sizeof (uchBuf));


        for (i = 0; i < nLength; ++i) {
            snprintf(chOut, sizeof (chOut), "%02X", uchBuf[i]);
            //wacscrn_output(nRow,i*3,chOut);
        }
        for (i = 0; i < nLength; ++i) {
            snprintf(chOut, sizeof (chOut), "%c", isprint(uchBuf[i]) ?
                    uchBuf[i] : '.');
            //wacscrn_output(nRow,60 + i,chOut);
        }

        if ((i = WacomParseData(hTablet, uchBuf, nLength, &state))) {
            snprintf(chOut, sizeof (chOut),
                    "ParseData: %10d %-40s (%d)",
                    i, strerror(errno), ++nErrors);
            /*wacscrn_output(23,0,chOut);
            wacscrn_refresh();*/
            //continue;
        }

        if (state.uValid & BIT(5)) {
            int prox = state.values[2].nValue;
            if (prox == 1) {

                int X, Y, B;



                X = state.values[4].nValue;
                Y = state.values[5].nValue;
                B = state.values[WACOMFIELD_BUTTONS].nValue & (1 << 5) ? 1 : 0;
                *lT = delay;
                *lX = X;
                *lY = 33440 - Y;
                *lB = B;
                
                valid=1;

              //  printf("valid=%d\n",valid);

            }
        }
        /*int fd = WacomGetFileDescriptor(hTablet);
        int unused[100];
        int imp = read(fd, &unused, 50);
        if (imp==0) {
            we_stop = 1;
        } else {
            lseek(fd, -imp, SEEK_CUR);
        }
        printf("fd=%d,imp=%d,we_stop=%d\n",fd,imp,we_stop);*/

    }
    //printf("j=%d\n",j);
    return j;
}

int mwOpen(const char *title) {

    pszFile = title;
    
   // printf("Le chemins : %s\n",title);
    
    
    /* check for device */
    /*if (!pszFile) {
        Usage();
        exit(1);
    }*/

    /* check for log file */
    /*  if (pszLogFile) {
          gLogFile = fopen(pszLogFile, "w");
          if (!gLogFile) {
              perror("failed to open log file for writing");
              exit(1);
          }
      }*/

    /* set device class, if provided */
    if (pszDeviceCls) {
        model.uClass = WacomGetClassFromName(pszDeviceCls);
        if (!model.uClass) {
            fprintf(stderr, "Unrecognized class: %s\n", pszDeviceCls);
            exit(1);
        }
    }

    /* set device type, if provided */
    if (pszDeviceType) {
        model.uDevice = WacomGetDeviceFromName(pszDeviceType, model.uClass);
        if (!model.uDevice) {
            fprintf(stderr, "Unrecognized device: %s\n", pszDeviceType);
            exit(1);
        }
    }

    /* open engine */
    hEngine = WacomInitEngine();
    if (!hEngine) {
        perror("failed to open tablet engine");
        exit(1);
    }

    /*WacomSetLogFunc(hEngine, LogCallback);
    WacomSetLogLevel(hEngine, (WACOMLOGLEVEL) nLevel);

    WacomLog(hEngine, WACOMLOGLEVEL_INFO, "Opening log");*/

    /* open tablet */
    hTablet = WacomOpenTablet(hEngine, pszFile, &model);
    if (!hTablet) {
        perror("WacomOpenTablet");
        exit(1);
    }

    /* get device capabilities, build screen */
    if (InitTablet(hTablet)) {
        perror("InitTablet");
        WacomCloseTablet(hTablet);
        exit(1);
    }
    
    
    gettimeofday(&sto, 0);
    
    pthread_t t;
     /* Creation du thread du magasin. */
   //printf ("Creation du thread du magasin !\n");
   int ret = pthread_create (&t, NULL,sampler, NULL);


   /* Creation des threads des clients si celui du magasin a reussi. */
   if (ret){
      fprintf (stderr, "%s", strerror (ret));
   }
   
   
    
    return 1;
}

int mwClose() {
    /* close device */
    WacomCloseTablet(hTablet);
    WacomTermEngine(hEngine);
}

int i=0;
float lesX[128], lesY[128]; 
int lesButton[128]; 
long lesTimes[128];



void * sampler(void *p){
    //printf("IN\n"); 
    while (1) {
      // 
       
        struct timeval st;
        gettimeofday(&st, 0);
        long curr_time = st.tv_usec / 1000 + 1000 * (st.tv_sec - sto.tv_sec);
        //long curr_time = st.tv_sec;
        long del = curr_time - last_time;
        //printf("%ld %ld\n",st.tv_usec,st.tv_sec);
        if (del < delay) {
            usleep((delay - del)*1000);
        }
        pthread_mutex_lock (&mutex_stock);
        if(i>=128){
            i=0;
        } 
       //  printf("IN\n"); 
        GetFirstValid(hTablet, lesX + i, lesY + i, lesButton + i, lesTimes + i);
       // printf("%f %f\n",lesX[i],lesY[i]); 
        i++;
        pthread_mutex_unlock (&mutex_stock);
        last_time = curr_time;
}
    
}

int mwGetPacket(float olesX[], float olesY[], int olesButton[], long olesTimes[]) {
     pthread_mutex_lock (&mutex_stock);
    int j=0;int n=i;
    for(j=0;j<i;j++){
        olesX[j] = lesX[j];
        olesY[j] = lesY[j];
        olesButton[j] = lesButton[j];
        olesTimes[j] = lesTimes[j];
    }
    i=0;
    pthread_mutex_unlock (&mutex_stock);
    return n;
}

int main() {
    mwOpen("dev");
    float lesX[128];
    float lesY[128];
    long lesT[128];
    int lesB[128];

    while (1) {
        usleep(50000);
        int res = mwGetPacket(lesX, lesY, lesB, lesT);
        printf("N=%d\n", res);
        int i = 0;
        for (i = 0; i < res; i++) {
            printf("%f,%f,%d,%ld\n", lesX[i], lesY[i], lesB[i], lesT[i]);
        }
    }



    return 0;
}
