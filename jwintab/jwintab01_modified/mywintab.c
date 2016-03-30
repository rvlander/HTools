/* $Id: mywintab.c,v 1.2 2000/02/01 05:40:21 rekimoto Exp $ */

#include <stdio.h>
#include <string.h>
#include <windows.h>
#include <stdlib.h>

/* wintab */
#include <wintab.h>
#ifdef USE_X_LIB
#include <wintabx.h>
#endif
//#define PACKETDATA	(PK_X | PK_Y | PK_BUTTONS | PK_ORIENTATION | PK_NORMAL_PRESSURE | PK_TIME)
#define PACKETDATA	(PK_X | PK_Y | PK_BUTTONS | PK_TIME)
#define PACKETMODE	(PK_TIME)
#include <pktdef.h>

#include "mywintab.h"

static HCTX ctx;

long screenX;
long screenY;
long minX;
long minY;
long maxX;
long maxY;

static HCTX TabletInit(HWND hWnd) {
	LOGCONTEXT lcMine;
	AXIS axisX;
	AXIS axisY;
	int marginX;
	int marginY;

	/* get default region */
	WTInfo(WTI_DEFCONTEXT, 0, &lcMine);
	WTInfo(WTI_DEVICES, DVC_X, &axisX);
	WTInfo(WTI_DEVICES, DVC_Y, &axisY);
	WTInfo(WTI_DEVICES, DVC_XMARGIN, &marginX);
	WTInfo(WTI_DEVICES, DVC_YMARGIN, &marginY);


	/* modify the digitizing region */
	strcpy(lcMine.lcName, "Rule Digitizing");
	lcMine.lcPktData = PACKETDATA;
	lcMine.lcPktMode = PACKETMODE;
	lcMine.lcMoveMask = 0;
	lcMine.lcBtnUpMask = lcMine.lcBtnDnMask;



	

	/*int resxmax = 53019;
        int resxmin = 29916;
        int resymax = 29166;
        int resymin = 3336;
        int screenX = 1280;
        int screenY = 800;*/

	//screenX = lcMine.lcSysExtX;
	//screenY = lcMine.lcSysExtY;
	screenX = 1280;
	screenY = 800;

	/*minX = axisX.axMin;
	minY = axisY.axMin;
	maxX = axisX.axMax;
	maxY = axisY.axMax;*/

	minX = 29916;
	minY = 3336;
	maxX = 53019;
	maxY = 29166;

	printf("Screen X:%d Screen Y:%d minX:%d maxX: %d minY:%d maxY%d \n",
		screenX,screenY,marginX,maxX,marginY,maxY);

	/* open the region */
	return WTOpen(hWnd, &lcMine, TRUE);
}

int mwOpen(HWND hWnd) {
	//HWND hWnd;	
	
	/*if (ctx != NULL) return -1;
	hWnd = GetDesktopWindow();*/
	if (hWnd == NULL) return -1;
	ctx = TabletInit(hWnd);
	if (ctx == NULL) return -1;

	WTQueueSizeSet(ctx, 128);
	

	return 1;
}

int mwClose() {
	if (ctx == NULL) return 1;
	return WTClose(ctx);
}

int mwGetPacket(float lesX[],float lesY[],int lesButton[],long lesTimes[]) {
	PACKET p[128];
	int res;
	int i=0;
	
	if (ctx == NULL) return -1;
	res = WTPacketsGet(ctx, 128, &p);
	//printf("%d\n",res);
	for(i=0;i<res;i++){
//printf("in\n",res);
		float x = p[i].pkX;//screenX * (float)(p[i].pkX - minX) / (float)(maxX-minX);
		float y = p[i].pkY;//screenY - screenY * (float)(p[i].pkY - minY) / (float)(maxY-minY);
		lesX[i] = x;
		lesY[i] = y;
		/*lesX[i] = p[i].pkX;
		lesY[i] = p[i].pkY;*/
		lesButton[i] = p[i].pkButtons;
		lesTimes[i] = p[i].pkTime;
		//printf("X:%d Y:%d B:%d T:%d \n",p[i].pkX,p[i].pkY, p[i].pkButtons, p[i].pkTime);
	}
	/*if ( res > 1) { 
		int i = res-1;
		val[0] = p[i].pkX;
		val[1] = p[i].pkY;
		val[2] = p[i].pkButtons;	
		val[3] = p[i].pkOrientation.orAzimuth;
		val[4] = p[i].pkOrientation.orAltitude;
		// added 1.29.2000
		val[5] = p[i].pkNormalPressure;
		val[6] = p[i].pkTime;
	}*/
	return res; 
}
