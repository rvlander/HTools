package hollermap.MCJImports;



import static org.mc.mcjavacore.MCJOperators.*;
import static org.mc.mcfunctions.MCJFunctions.*;
import org.mc.mcfunctions.MCJOutputArg;
import static org.mc.mcjavautils.MCJUtils.*;
import static org.mc.mcjavacore.MCJBaseFunctions.*;
public class init{
static final double[][] pi = matrixFromDouble(Math.PI);
public static double[][] init(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] T= new double[0][0];
if(nargin[0][0]>0)T= iargs[0];
double[][] X= new double[0][0];
if(nargin[0][0]>1)X= iargs[1];
double[][] Y= new double[0][0];
if(nargin[0][0]>2)Y= iargs[2];
double[][] cxbool= new double[0][0];
if(nargin[0][0]>3)cxbool= iargs[3];
double[][] cybool= new double[0][0];
if(nargin[0][0]>4)cybool= iargs[4];
double[][] nT= new double[0][0];
double[][] dXsdT= new double[0][0];
double[][] dYsdT= new double[0][0];
double[][] ind0xs= new double[0][0];
double[][] t0xs= new double[0][0];
double[][] x0s= new double[0][0];
double[][] ind0ys= new double[0][0];
double[][] t0ys= new double[0][0];
double[][] y0s= new double[0][0];
double[][] cx= new double[0][0];
double[][] cy= new double[0][0];
double[][] filtereddYsdT= new double[0][0];
double[][] n = new double[0][0];
double[][] dT = new double[0][0];
double[][] dX = new double[0][0];
double[][] dY = new double[0][0];
double[][] aT = new double[0][0];
double[][] filtereddXsdT = new double[0][0];
label:do{
n=size(null,new double[][][]{T,matrixFromDouble(1)});
cx=matrixFromDouble(0);
cy=matrixFromDouble(0);
if(any(cxbool)){cx=mrdivide((minus(subsref(X,new double[][][]{n}),subsref(X,new double[][][]{matrixFromDouble(1)}))),(minus(subsref(T,new double[][][]{n}),subsref(T,new double[][][]{matrixFromDouble(1)}))));
};
if(any(cybool)){cy=mrdivide((minus(subsref(Y,new double[][][]{n}),subsref(Y,new double[][][]{matrixFromDouble(1)}))),(minus(subsref(T,new double[][][]{n}),subsref(T,new double[][][]{matrixFromDouble(1)}))));
};
dT=diff(null,new double[][][]{T});
dX=diff(null,new double[][][]{X});
dY=diff(null,new double[][][]{Y});
dXsdT=minus(rdivide(dX,dT),cx);
dYsdT=minus(rdivide(dY,dT),cy);
aT=add(subsref(T,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),minus(n,matrixFromDouble(1)))}),subsref(T,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),n)}));
nT=mrdivide(aT,matrixFromDouble(2));
dXsdT=vertcat(vertcat(matrixFromDouble(0),dXsdT),matrixFromDouble(0));
dYsdT=vertcat(vertcat(matrixFromDouble(0),dYsdT),matrixFromDouble(0));
nT=vertcat(vertcat(subsref(T,new double[][][]{matrixFromDouble(1)}),nT),subsref(T,new double[][][]{numel(T)}));
filtereddXsdT=filtfilt(null,new double[][][]{mrdivide(ones(null,new double[][][]{matrixFromDouble(1),matrixFromDouble(6)}),matrixFromDouble(6)),matrixFromDouble(1),dXsdT});
filtereddYsdT=filtfilt(null,new double[][][]{mrdivide(ones(null,new double[][][]{matrixFromDouble(1),matrixFromDouble(6)}),matrixFromDouble(6)),matrixFromDouble(1),dYsdT});
MCJOutputArg[] oa0;
oa0 = new MCJOutputArg[3];
oa0[0]= new MCJOutputArg();
oa0[1]= new MCJOutputArg();
oa0[2]= new MCJOutputArg();
crossing(oa0,new double[][][]{filtereddXsdT,nT});
ind0xs=oa0[0].val;
t0xs=oa0[1].val;
x0s=oa0[2].val;
MCJOutputArg[] oa1;
oa1 = new MCJOutputArg[3];
oa1[0]= new MCJOutputArg();
oa1[1]= new MCJOutputArg();
oa1[2]= new MCJOutputArg();
crossing(oa1,new double[][][]{filtereddYsdT,nT});
ind0ys=oa1[0].val;
t0ys=oa1[1].val;
y0s=oa1[2].val;
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=nT;
if(oargs.length>1)oargs[1].val=dXsdT;
if(oargs.length>2)oargs[2].val=dYsdT;
if(oargs.length>3)oargs[3].val=ind0xs;
if(oargs.length>4)oargs[4].val=t0xs;
if(oargs.length>5)oargs[5].val=x0s;
if(oargs.length>6)oargs[6].val=ind0ys;
if(oargs.length>7)oargs[7].val=t0ys;
if(oargs.length>8)oargs[8].val=y0s;
if(oargs.length>9)oargs[9].val=cx;
if(oargs.length>10)oargs[10].val=cy;
if(oargs.length>11)oargs[11].val=filtereddYsdT;
}
return nT;
}

public static double[][] crossing(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] S= new double[0][0];
if(nargin[0][0]>0)S= iargs[0];
double[][] t= new double[0][0];
if(nargin[0][0]>1)t= iargs[1];
double[][] level= new double[0][0];
if(nargin[0][0]>2)level= iargs[2];
double[][] imeth= new double[0][0];
if(nargin[0][0]>3)imeth= iargs[3];
double[][] ind= new double[0][0];
double[][] t0= new double[0][0];
double[][] s0= new double[0][0];
double[][] ind0 = new double[0][0];
double[][] S1 = new double[0][0];
double[][] ind1 = new double[0][0];
double[][] NUM = new double[0][0];
double[][] DEN = new double[0][0];
double[][] DELTA = new double[0][0];
label:do{
error(null,new double[][][]{nargchk(null,new double[][][]{matrixFromDouble(1),matrixFromDouble(4),nargin})});
if(any(lor(lt(nargin,matrixFromDouble(2)),isempty(null,new double[][][]{t})))){t=colon(matrixFromDouble(1),matrixFromDouble(1),length(null,new double[][][]{S}));
}else if(all(ne(length(null,new double[][][]{t}),length(null,new double[][][]{S})))){error(null,new double[][][]{matrixFromText("t and S must be of identical length!")});
};
if(any(lt(nargin,matrixFromDouble(3)))){level=matrixFromDouble(0);
};
if(any(lt(nargin,matrixFromDouble(4)))){imeth=matrixFromText("linear");
};
t=ctranspose(subsref(t,new double[][][]{null}));
S=ctranspose(subsref(S,new double[][][]{null}));
S=minus(S,level);
ind0=find(null,new double[][][]{eq(S,matrixFromDouble(0))});
S1=times(subsref(S,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),minus(numel(S),matrixFromDouble(1)))}),subsref(S,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),numel(S))}));
ind1=find(null,new double[][][]{lt(S1,matrixFromDouble(0))});
ind=sort(null,new double[][][]{horzcat(ind0,ind1)});
t0=subsref(t,new double[][][]{ind});
s0=subsref(S,new double[][][]{ind});
if(any(strcmp(null,new double[][][]{imeth,matrixFromText("linear")}))){double[][] fortemp0 =colon(matrixFromDouble(1),matrixFromDouble(1),length(null,new double[][][]{t0}));
 for(int posdfo=0;posdfo<fortemp0.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp0[0].length;sdfgsdfgdf++){ 
double[][] ii = matrixFromDouble(fortemp0[posdfo][sdfgsdfgdf]);
if(any(gt(abs(null,new double[][][]{subsref(S,new double[][][]{subsref(ind,new double[][][]{ii})})}),eps(null,new double[][][]{subsref(S,new double[][][]{subsref(ind,new double[][][]{ii})})})))){NUM=(minus(subsref(t,new double[][][]{add(subsref(ind,new double[][][]{ii}),matrixFromDouble(1))}),subsref(t,new double[][][]{subsref(ind,new double[][][]{ii})})));
DEN=(minus(subsref(S,new double[][][]{add(subsref(ind,new double[][][]{ii}),matrixFromDouble(1))}),subsref(S,new double[][][]{subsref(ind,new double[][][]{ii})})));
DELTA=mrdivide(NUM,DEN);
t0 = subsasgn(t0,new double[][][]{ii},minus(subsref(t0,new double[][][]{ii}),mtimes(subsref(S,new double[][][]{subsref(ind,new double[][][]{ii})}),DELTA)));
s0 = subsasgn(s0,new double[][][]{ii},matrixFromDouble(0));
};
}
}
;
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=ind;
if(oargs.length>1)oargs[1].val=t0;
if(oargs.length>2)oargs[2].val=s0;
}
return ind;
}

public static double[][] strcmp(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] S1= new double[0][0];
if(nargin[0][0]>0)S1= iargs[0];
double[][] S2= new double[0][0];
if(nargin[0][0]>1)S2= iargs[1];
double[][] res= new double[0][0];
label:do{
res=eq(S1,S2);
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=res;
}
return res;
}

public static double[][] length(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] R= new double[0][0];
label:do{
R=max(null,new double[][][]{size(null,new double[][][]{X})});
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=R;
}
return R;
}

public static double[][] isempty(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] res= new double[0][0];
label:do{
if(any((eq(numel(null,new double[][][]{X}),matrixFromDouble(0))))){res=matrixFromDouble(1);
}else{res=matrixFromDouble(0);
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=res;
}
return res;
}

public static double[][] nargchk(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] low= new double[0][0];
if(nargin[0][0]>0)low= iargs[0];
double[][] high= new double[0][0];
if(nargin[0][0]>1)high= iargs[1];
double[][] n= new double[0][0];
if(nargin[0][0]>2)n= iargs[2];
double[][] unused= new double[0][0];
if(nargin[0][0]>3)unused= iargs[3];
double[][] MSG= new double[0][0];
label:do{
MSG=matrixFromText("");
if(any((lor(lt(n,low),gt(n,high))))){MSG=matrixFromText("arg check failed");
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=MSG;
}
return MSG;
}

public static double[][] filtfilt(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] b= new double[0][0];
if(nargin[0][0]>0)b= iargs[0];
double[][] a= new double[0][0];
if(nargin[0][0]>1)a= iargs[1];
double[][] x= new double[0][0];
if(nargin[0][0]>2)x= iargs[2];
double[][] y= new double[0][0];
double[][] isRowVec = new double[0][0];
double[][] Npts = new double[0][0];
double[][] Nchans = new double[0][0];
double[][] zi = new double[0][0];
double[][] nfact = new double[0][0];
double[][] L = new double[0][0];
label:do{
error(null,new double[][][]{nargchk(null,new double[][][]{matrixFromDouble(3),matrixFromDouble(3),nargin,matrixFromText("struct")})});
if(any(lor(lor(not(isa(null,new double[][][]{b,matrixFromText("double")})),not(isa(null,new double[][][]{a,matrixFromText("double")}))),not(isa(null,new double[][][]{x,matrixFromText("double")}))))){error(null,new double[][][]{message(null,new double[][][]{matrixFromText("signal:filtfilt:NotSupported")})});
};
if(any(lor(lor(isempty(null,new double[][][]{b}),isempty(null,new double[][][]{a})),isempty(null,new double[][][]{x})))){y=new double[0][0];
break label;
};
isRowVec=eq(size(null,new double[][][]{x,matrixFromDouble(1)}),matrixFromDouble(1));
if(any(isRowVec)){x=subsref(x,new double[][][]{null});
};
MCJOutputArg[] oa2;
oa2 = new MCJOutputArg[2];
oa2[0]= new MCJOutputArg();
oa2[1]= new MCJOutputArg();
size(oa2,new double[][][]{x});
Npts=oa2[0].val;
Nchans=oa2[1].val;
MCJOutputArg[] oa3;
oa3 = new MCJOutputArg[5];
oa3[0]= new MCJOutputArg();
oa3[1]= new MCJOutputArg();
oa3[2]= new MCJOutputArg();
oa3[3]= new MCJOutputArg();
oa3[4]= new MCJOutputArg();
getCoeffsAndInitialConditions(oa3,new double[][][]{b,a,Npts});
b=oa3[0].val;
a=oa3[1].val;
zi=oa3[2].val;
nfact=oa3[3].val;
L=oa3[4].val;
if(any(eq(Nchans,matrixFromDouble(1)))){if(any(lt(Npts,matrixFromDouble(10000)))){y=ffOneChanCat(null,new double[][][]{b,a,x,zi,nfact,L});
}else{y=ffOneChan(null,new double[][][]{b,a,x,zi,nfact,L});
};
}else{y=ffMultiChan(null,new double[][][]{b,a,x,zi,nfact,L});
};
if(any(isRowVec)){y=transpose(y);
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
}

public static double[][] getCoeffsAndInitialConditions(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] b= new double[0][0];
if(nargin[0][0]>0)b= iargs[0];
double[][] a= new double[0][0];
if(nargin[0][0]>1)a= iargs[1];
double[][] Npts= new double[0][0];
if(nargin[0][0]>2)Npts= iargs[2];
double[][] zi= new double[0][0];
double[][] nfact= new double[0][0];
double[][] L= new double[0][0];
double[][] ncols = new double[0][0];
double[][] na = new double[0][0];
double[][] issos = new double[0][0];
double[][] g = new double[0][0];
double[][] ng = new double[0][0];
double[][] rhs = new double[0][0];
double[][] nb = new double[0][0];
double[][] nfilt = new double[0][0];
double[][] rows = new double[0][0];
double[][] cols = new double[0][0];
double[][] vals = new double[0][0];
label:do{
MCJOutputArg[] oa4;
oa4 = new MCJOutputArg[2];
oa4[0]= new MCJOutputArg();
oa4[1]= new MCJOutputArg();
size(oa4,new double[][][]{b});
L=oa4[0].val;
ncols=oa4[1].val;
na=numel(null,new double[][][]{a});
if(any(land(land(eq(ncols,matrixFromDouble(6)),eq(L,matrixFromDouble(1))),le(na,matrixFromDouble(2))))){if(any(eq(subsref(b,new double[][][]{matrixFromDouble(4)}),matrixFromDouble(1)))){warning(null,new double[][][]{message(null,new double[][][]{matrixFromText("signal:filtfilt:ParseSOS"),matrixFromText("SOS"),matrixFromText("G")})});
}else{warning(null,new double[][][]{message(null,new double[][][]{matrixFromText("signal:filtfilt:ParseB"),matrixFromText("a01"),matrixFromText("SOS")})});
};
};
issos=land(eq(ncols,matrixFromDouble(6)),(lor(gt(L,matrixFromDouble(1)),(land(eq(subsref(b,new double[][][]{matrixFromDouble(4)}),matrixFromDouble(1)),le(na,matrixFromDouble(2)))))));
if(any(issos)){g=subsref(a,new double[][][]{null});
ng=na;
if(any(gt(ng,add(L,matrixFromDouble(1))))){error(null,new double[][][]{message(null,new double[][][]{matrixFromText("signal:filtfilt:InvalidDimensionsScaleValues"),add(L,matrixFromDouble(1))})});
}else if(all(eq(ng,add(L,matrixFromDouble(1))))){b = subsasgn(b,new double[][][]{L,colon(matrixFromDouble(1),matrixFromDouble(1),matrixFromDouble(3))},mtimes(subsref(g,new double[][][]{add(L,matrixFromDouble(1))}),subsref(b,new double[][][]{L,colon(matrixFromDouble(1),matrixFromDouble(1),matrixFromDouble(3))})));
ng=minus(ng,matrixFromDouble(1));
};
double[][] fortemp1 =colon(matrixFromDouble(1),matrixFromDouble(1),ng);
 for(int posdfo=0;posdfo<fortemp1.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp1[0].length;sdfgsdfgdf++){ 
double[][] ii = matrixFromDouble(fortemp1[posdfo][sdfgsdfgdf]);
b = subsasgn(b,new double[][][]{ii,colon(matrixFromDouble(1),matrixFromDouble(1),matrixFromDouble(3))},mtimes(subsref(g,new double[][][]{ii}),subsref(b,new double[][][]{ii,colon(matrixFromDouble(1),matrixFromDouble(1),matrixFromDouble(3))})));
}
}
;
a=transpose(subsref(b,new double[][][]{null,colon(matrixFromDouble(4),matrixFromDouble(1),matrixFromDouble(6))}));
b=transpose(subsref(b,new double[][][]{null,colon(matrixFromDouble(1),matrixFromDouble(1),matrixFromDouble(3))}));
nfact=matrixFromDouble(6);
if(any(le(Npts,nfact))){error(null,new double[][][]{message(null,new double[][][]{matrixFromText("signal:filtfilt:InvalidDimensionsData")})});
};
zi=zeros(null,new double[][][]{matrixFromDouble(2),L});
double[][] fortemp2 =colon(matrixFromDouble(1),matrixFromDouble(1),L);
 for(int posdfo=0;posdfo<fortemp2.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp2[0].length;sdfgsdfgdf++){ 
double[][] ii = matrixFromDouble(fortemp2[posdfo][sdfgsdfgdf]);
rhs=(minus(subsref(b,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),matrixFromDouble(3)),ii}),mtimes(subsref(b,new double[][][]{matrixFromDouble(1),ii}),subsref(a,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),matrixFromDouble(3)),ii}))));
zi = subsasgn(zi,new double[][][]{null,ii},mldivide((minus(eye(null,new double[][][]{matrixFromDouble(2)}),horzcat(uminus(subsref(a,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),matrixFromDouble(3)),ii})),vertcat(matrixFromDouble(1),matrixFromDouble(0))))),rhs));
}
}
;
}else{L=matrixFromDouble(1);
b=subsref(b,new double[][][]{null});
a=subsref(a,new double[][][]{null});
nb=numel(null,new double[][][]{b});
nfilt=max(null,new double[][][]{nb,na});
nfact=mtimes(matrixFromDouble(3),(minus(nfilt,matrixFromDouble(1))));
if(any(le(Npts,nfact))){error(null,new double[][][]{message(null,new double[][][]{matrixFromText("signal:filtfilt:InvalidDimensionsDataShortForFiltOrder")})});
};
if(any(lt(nb,nfilt))){b = subsasgn(b,new double[][][]{nfilt,matrixFromDouble(1)},matrixFromDouble(0));
}else if(all(lt(na,nfilt))){a = subsasgn(a,new double[][][]{nfilt,matrixFromDouble(1)},matrixFromDouble(0));
};
if(any(gt(nfilt,matrixFromDouble(1)))){rows=horzcat(horzcat(colon(matrixFromDouble(1),matrixFromDouble(1),minus(nfilt,matrixFromDouble(1))),colon(matrixFromDouble(2),matrixFromDouble(1),minus(nfilt,matrixFromDouble(1)))),colon(matrixFromDouble(1),matrixFromDouble(1),minus(nfilt,matrixFromDouble(2))));
cols=horzcat(horzcat(ones(null,new double[][][]{matrixFromDouble(1),minus(nfilt,matrixFromDouble(1))}),colon(matrixFromDouble(2),matrixFromDouble(1),minus(nfilt,matrixFromDouble(1)))),colon(matrixFromDouble(2),matrixFromDouble(1),minus(nfilt,matrixFromDouble(1))));
vals=horzcat(horzcat(horzcat(add(matrixFromDouble(1),subsref(a,new double[][][]{matrixFromDouble(2)})),transpose(subsref(a,new double[][][]{colon(matrixFromDouble(3),matrixFromDouble(1),nfilt)}))),ones(null,new double[][][]{matrixFromDouble(1),minus(nfilt,matrixFromDouble(2))})),uminus(ones(null,new double[][][]{matrixFromDouble(1),minus(nfilt,matrixFromDouble(2))})));
rhs=minus(subsref(b,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),nfilt)}),mtimes(subsref(b,new double[][][]{matrixFromDouble(1)}),subsref(a,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),nfilt)})));
zi=mldivide(sparse(null,new double[][][]{rows,cols,vals}),rhs);
}else{zi=zeros(null,new double[][][]{matrixFromDouble(0),matrixFromDouble(1)});
};
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=b;
if(oargs.length>1)oargs[1].val=a;
if(oargs.length>2)oargs[2].val=zi;
if(oargs.length>3)oargs[3].val=nfact;
if(oargs.length>4)oargs[4].val=L;
}
return b;
}

public static double[][] ffOneChanCat(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] b= new double[0][0];
if(nargin[0][0]>0)b= iargs[0];
double[][] a= new double[0][0];
if(nargin[0][0]>1)a= iargs[1];
double[][] y= new double[0][0];
if(nargin[0][0]>2)y= iargs[2];
double[][] zi= new double[0][0];
if(nargin[0][0]>3)zi= iargs[3];
double[][] nfact= new double[0][0];
if(nargin[0][0]>4)nfact= iargs[4];
double[][] L= new double[0][0];
if(nargin[0][0]>5)L= iargs[5];
label:do{
double[][] fortemp3 =colon(matrixFromDouble(1),matrixFromDouble(1),L);
 for(int posdfo=0;posdfo<fortemp3.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp3[0].length;sdfgsdfgdf++){ 
double[][] ii = matrixFromDouble(fortemp3[posdfo][sdfgsdfgdf]);
y=vertcat(vertcat(minus(mtimes(matrixFromDouble(2),subsref(y,new double[][][]{matrixFromDouble(1)})),subsref(y,new double[][][]{colon(add(nfact,matrixFromDouble(1)),uminus(matrixFromDouble(1)),matrixFromDouble(2))})),y),minus(mtimes(matrixFromDouble(2),subsref(y,new double[][][]{numel(y)})),subsref(y,new double[][][]{colon(minus(numel(y),matrixFromDouble(1)),uminus(matrixFromDouble(1)),minus(numel(y),nfact))})));
y=filter(null,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),y,mtimes(subsref(zi,new double[][][]{null,ii}),subsref(y,new double[][][]{matrixFromDouble(1)}))});
y=subsref(y,new double[][][]{colon(numel(y),uminus(matrixFromDouble(1)),matrixFromDouble(1))});
y=filter(null,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),y,mtimes(subsref(zi,new double[][][]{null,ii}),subsref(y,new double[][][]{matrixFromDouble(1)}))});
y=subsref(y,new double[][][]{colon(minus(numel(y),nfact),uminus(matrixFromDouble(1)),add(nfact,matrixFromDouble(1)))});
}
}
;
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
}

public static double[][] ffOneChan(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] b= new double[0][0];
if(nargin[0][0]>0)b= iargs[0];
double[][] a= new double[0][0];
if(nargin[0][0]>1)a= iargs[1];
double[][] xc= new double[0][0];
if(nargin[0][0]>2)xc= iargs[2];
double[][] zi= new double[0][0];
if(nargin[0][0]>3)zi= iargs[3];
double[][] nfact= new double[0][0];
if(nargin[0][0]>4)nfact= iargs[4];
double[][] L= new double[0][0];
if(nargin[0][0]>5)L= iargs[5];
double[][] y= new double[0][0];
double[][] xt = new double[0][0];
double[][] ignored_arg = new double[0][0];
double[][] zo = new double[0][0];
double[][] yc2 = new double[0][0];
double[][] yc3 = new double[0][0];
double[][] yc5 = new double[0][0];
label:do{
double[][] fortemp4 =colon(matrixFromDouble(1),matrixFromDouble(1),L);
 for(int posdfo=0;posdfo<fortemp4.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp4[0].length;sdfgsdfgdf++){ 
double[][] ii = matrixFromDouble(fortemp4[posdfo][sdfgsdfgdf]);
xt=add(uminus(subsref(xc,new double[][][]{colon(add(nfact,matrixFromDouble(1)),uminus(matrixFromDouble(1)),matrixFromDouble(2))})),mtimes(matrixFromDouble(2),subsref(xc,new double[][][]{matrixFromDouble(1)})));
MCJOutputArg[] oa5;
oa5 = new MCJOutputArg[2];
oa5[0]= new MCJOutputArg();
oa5[1]= new MCJOutputArg();
filter(oa5,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),xt,mtimes(subsref(zi,new double[][][]{null,ii}),subsref(xt,new double[][][]{matrixFromDouble(1)}))});
ignored_arg=oa5[0].val;
zo=oa5[1].val;
MCJOutputArg[] oa6;
oa6 = new MCJOutputArg[2];
oa6[0]= new MCJOutputArg();
oa6[1]= new MCJOutputArg();
filter(oa6,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),xc,zo});
yc2=oa6[0].val;
zo=oa6[1].val;
xt=add(uminus(subsref(xc,new double[][][]{colon(minus(numel(xc),matrixFromDouble(1)),uminus(matrixFromDouble(1)),minus(numel(xc),nfact))})),mtimes(matrixFromDouble(2),subsref(xc,new double[][][]{numel(xc)})));
yc3=filter(null,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),xt,zo});
MCJOutputArg[] oa7;
oa7 = new MCJOutputArg[2];
oa7[0]= new MCJOutputArg();
oa7[1]= new MCJOutputArg();
filter(oa7,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),subsref(yc3,new double[][][]{colon(numel(yc3),uminus(matrixFromDouble(1)),matrixFromDouble(1))}),mtimes(subsref(zi,new double[][][]{null,ii}),subsref(yc3,new double[][][]{numel(yc3)}))});
ignored_arg=oa7[0].val;
zo=oa7[1].val;
yc5=filter(null,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),subsref(yc2,new double[][][]{colon(numel(yc2),uminus(matrixFromDouble(1)),matrixFromDouble(1))}),zo});
xc=subsref(yc5,new double[][][]{colon(numel(yc5),uminus(matrixFromDouble(1)),matrixFromDouble(1))});
}
}
;
y=xc;
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
}

public static double[][] ffMultiChan(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] b= new double[0][0];
if(nargin[0][0]>0)b= iargs[0];
double[][] a= new double[0][0];
if(nargin[0][0]>1)a= iargs[1];
double[][] xc= new double[0][0];
if(nargin[0][0]>2)xc= iargs[2];
double[][] zi= new double[0][0];
if(nargin[0][0]>3)zi= iargs[3];
double[][] nfact= new double[0][0];
if(nargin[0][0]>4)nfact= iargs[4];
double[][] L= new double[0][0];
if(nargin[0][0]>5)L= iargs[5];
double[][] y= new double[0][0];
double[][] sz = new double[0][0];
double[][] xt = new double[0][0];
double[][] ignored_arg = new double[0][0];
double[][] zo = new double[0][0];
double[][] yc2 = new double[0][0];
double[][] yc3 = new double[0][0];
double[][] yc5 = new double[0][0];
label:do{
sz=size(null,new double[][][]{xc});
xc=reshape(null,new double[][][]{xc,subsref(sz,new double[][][]{matrixFromDouble(1)}),new double[0][0]});
double[][] fortemp5 =colon(matrixFromDouble(1),matrixFromDouble(1),L);
 for(int posdfo=0;posdfo<fortemp5.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp5[0].length;sdfgsdfgdf++){ 
double[][] ii = matrixFromDouble(fortemp5[posdfo][sdfgsdfgdf]);
xt=bsxfun(null,new double[][][]{matrixFromText("minus"),mtimes(matrixFromDouble(2),subsref(xc,new double[][][]{matrixFromDouble(1),null})),subsref(xc,new double[][][]{colon(add(nfact,matrixFromDouble(1)),uminus(matrixFromDouble(1)),matrixFromDouble(2)),null})});
MCJOutputArg[] oa8;
oa8 = new MCJOutputArg[2];
oa8[0]= new MCJOutputArg();
oa8[1]= new MCJOutputArg();
filter(oa8,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),xt,mtimes(subsref(zi,new double[][][]{null,ii}),subsref(xt,new double[][][]{matrixFromDouble(1),null}))});
ignored_arg=oa8[0].val;
zo=oa8[1].val;
MCJOutputArg[] oa9;
oa9 = new MCJOutputArg[2];
oa9[0]= new MCJOutputArg();
oa9[1]= new MCJOutputArg();
filter(oa9,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),xc,zo});
yc2=oa9[0].val;
zo=oa9[1].val;
xt=bsxfun(null,new double[][][]{matrixFromText("minus"),mtimes(matrixFromDouble(2),subsref(xc,new double[][][]{matrixFromDouble(xc.length),null})),subsref(xc,new double[][][]{colon(minus(matrixFromDouble(xc.length),matrixFromDouble(1)),uminus(matrixFromDouble(1)),minus(matrixFromDouble(xc[0].length),nfact)),null})});
yc3=filter(null,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),xt,zo});
MCJOutputArg[] oa10;
oa10 = new MCJOutputArg[2];
oa10[0]= new MCJOutputArg();
oa10[1]= new MCJOutputArg();
filter(oa10,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),subsref(yc3,new double[][][]{colon(matrixFromDouble(yc3.length),uminus(matrixFromDouble(1)),matrixFromDouble(1)),null}),mtimes(subsref(zi,new double[][][]{null,ii}),subsref(yc3,new double[][][]{matrixFromDouble(yc3.length),null}))});
ignored_arg=oa10[0].val;
zo=oa10[1].val;
yc5=filter(null,new double[][][]{subsref(b,new double[][][]{null,ii}),subsref(a,new double[][][]{null,ii}),subsref(yc2,new double[][][]{colon(matrixFromDouble(yc2.length),uminus(matrixFromDouble(1)),matrixFromDouble(1)),null}),zo});
xc=subsref(yc5,new double[][][]{colon(matrixFromDouble(yc5.length),uminus(matrixFromDouble(1)),matrixFromDouble(1)),null});
}
}
;
y=xc;
y=reshape(null,new double[][][]{y,sz});
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
}

public static double[][] reshape(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] M= new double[0][0];
if(nargin[0][0]>1)M= iargs[1];
double[][] N= new double[0][0];
if(nargin[0][0]>2)N= iargs[2];
double[][] A= new double[0][0];
double[][] n_elem = new double[0][0];
label:do{
if(any((land(lt(nargin,matrixFromDouble(3)),gt(numel(null,new double[][][]{M}),matrixFromDouble(1)))))){N=subsref(M,new double[][][]{matrixFromDouble(2)});
M=subsref(M,new double[][][]{matrixFromDouble(1)});
};
if(any((ne(numel(null,new double[][][]{X}),mtimes(M,N))))){error(null,new double[][][]{matrixFromText("reshape : source and target matrix have a different number of elements")});
};
A=zeros(null,new double[][][]{M,N});
n_elem=mtimes(M,N);
A = subsasgn(A,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),n_elem)},X);
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=A;
}
return A;
}

public static double[][] filter(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] b= new double[0][0];
if(nargin[0][0]>0)b= iargs[0];
double[][] a= new double[0][0];
if(nargin[0][0]>1)a= iargs[1];
double[][] x= new double[0][0];
if(nargin[0][0]>2)x= iargs[2];
double[][] y= new double[0][0];
double[][] na = new double[0][0];
double[][] nb = new double[0][0];
double[][] p1 = new double[0][0];
double[][] p2 = new double[0][0];
label:do{
b=rdivide(b,subsref(a,new double[][][]{matrixFromDouble(1)}));
a=rdivide(a,subsref(a,new double[][][]{matrixFromDouble(1)}));
na=minus(numel(null,new double[][][]{a}),matrixFromDouble(1));
nb=minus(numel(null,new double[][][]{b}),matrixFromDouble(1));
y=zeros(null,new double[][][]{numel(null,new double[][][]{x}),matrixFromDouble(1)});
double[][] fortemp6 =colon(matrixFromDouble(1),matrixFromDouble(1),numel(null,new double[][][]{x}));
 for(int posdfo=0;posdfo<fortemp6.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp6[0].length;sdfgsdfgdf++){ 
double[][] n = matrixFromDouble(fortemp6[posdfo][sdfgsdfgdf]);
if(any((le(minus(n,nb),matrixFromDouble(0))))){p1=sum(null,new double[][][]{times(subsref(b,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),n)}),subsref(x,new double[][][]{colon(n,uminus(matrixFromDouble(1)),matrixFromDouble(1))}))});
}else{p1=sum(null,new double[][][]{times(b,subsref(x,new double[][][]{colon(n,uminus(matrixFromDouble(1)),minus(n,nb))}))});
};
if(any(le(minus(n,na),matrixFromDouble(0)))){p2=sum(null,new double[][][]{times(subsref(a,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),n)}),subsref(y,new double[][][]{colon(minus(n,matrixFromDouble(1)),uminus(matrixFromDouble(1)),matrixFromDouble(1))}))});
}else{p2=sum(null,new double[][][]{times(subsref(a,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),numel(a))}),subsref(y,new double[][][]{colon(minus(n,matrixFromDouble(1)),uminus(matrixFromDouble(1)),minus(n,na))}))});
};
y = subsasgn(y,new double[][][]{n},minus(p1,p2));
}
}
;
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
}

public static double[][] sparse(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] A= new double[0][0];
if(nargin[0][0]>0)A= iargs[0];
double[][] B= new double[0][0];
if(nargin[0][0]>1)B= iargs[1];
double[][] C= new double[0][0];
if(nargin[0][0]>2)C= iargs[2];
double[][] I= new double[0][0];
double[][] n = new double[0][0];
label:do{
if(any((eq(nargin,matrixFromDouble(1))))){I=A;
}else if(all((eq(nargin,matrixFromDouble(3))))){I=zeros(null,new double[][][]{max(null,new double[][][]{A}),max(null,new double[][][]{B})});
n=numel(null,new double[][][]{A});
double[][] fortemp7 =colon(matrixFromDouble(1),matrixFromDouble(1),n);
 for(int posdfo=0;posdfo<fortemp7.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp7[0].length;sdfgsdfgdf++){ 
double[][] i = matrixFromDouble(fortemp7[posdfo][sdfgsdfgdf]);
I = subsasgn(I,new double[][][]{subsref(A,new double[][][]{i}),subsref(B,new double[][][]{i})},subsref(C,new double[][][]{i}));
}
}
;
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=I;
}
return I;
}

public static double[][] eye(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] M= new double[0][0];
if(nargin[0][0]>0)M= iargs[0];
double[][] N= new double[0][0];
if(nargin[0][0]>1)N= iargs[1];
double[][] I= new double[0][0];
double[][] L = new double[0][0];
double[][] C = new double[0][0];
double[][] Ndim = new double[0][0];
label:do{
if(any((eq(nargin,matrixFromDouble(1))))){if(any((eq(numel(null,new double[][][]{M}),matrixFromDouble(2))))){L=subsref(M,new double[][][]{matrixFromDouble(1)});
C=subsref(M,new double[][][]{matrixFromDouble(2)});
}else{L=subsref(M,new double[][][]{matrixFromDouble(1)});
C=subsref(M,new double[][][]{matrixFromDouble(1)});
};
}else if(all((gt(nargin,matrixFromDouble(1))))){L=subsref(M,new double[][][]{matrixFromDouble(1)});
C=subsref(N,new double[][][]{matrixFromDouble(1)});
};
Ndim=uminus(max(null,new double[][][]{uminus(horzcat(L,C))}));
I=zeros(null,new double[][][]{L,C});
double[][] fortemp8 =colon(matrixFromDouble(1),matrixFromDouble(1),Ndim);
 for(int posdfo=0;posdfo<fortemp8.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp8[0].length;sdfgsdfgdf++){ 
double[][] i = matrixFromDouble(fortemp8[posdfo][sdfgsdfgdf]);
I = subsasgn(I,new double[][][]{i,i},matrixFromDouble(1));
}
}
;
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=I;
}
return I;
}

public static double[][] isa(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] input_args= new double[0][0];
if(nargin[0][0]>0)input_args= iargs[0];
double[][] B= new double[0][0];
label:do{
B=matrixFromDouble(1);
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=B;
}
return B;
}

public static double[][] ones(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] varargin= new double[0][0];
if(nargin[0][0]>0)varargin= iargs[0];
double[][] A= new double[0][0];
label:do{
A=add(zeros(null,iargs),matrixFromDouble(1));
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=A;
}
return A;
}

public static double[][] diff(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] A= new double[0][0];
double[][] n = new double[0][0];
label:do{
n=size(null,new double[][][]{X,matrixFromDouble(1)});
A=minus(subsref(X,new double[][][]{colon(matrixFromDouble(2),matrixFromDouble(1),n),null}),subsref(X,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),minus(n,matrixFromDouble(1))),null}));
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=A;
}
return A;
}

}
