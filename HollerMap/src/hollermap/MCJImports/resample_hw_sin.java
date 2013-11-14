package hollermap.MCJImports;



import static org.mc.mcjavacore.MCJOperators.*;
import static org.mc.mcfunctions.MCJFunctions.*;
import org.mc.mcfunctions.MCJOutputArg;
import static org.mc.mcjavautils.MCJUtils.*;
import static org.mc.mcjavacore.MCJBaseFunctions.*;
public class resample_hw_sin{
static final double[][] pi = matrixFromDouble(Math.PI);
public static double[][] resample_hw_sin(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] sampling= new double[0][0];
if(nargin[0][0]>0)sampling= iargs[0];
double[][] t0xs= new double[0][0];
if(nargin[0][0]>1)t0xs= iargs[1];
double[][] t0ys= new double[0][0];
if(nargin[0][0]>2)t0ys= iargs[2];
double[][] a= new double[0][0];
if(nargin[0][0]>3)a= iargs[3];
double[][] b= new double[0][0];
if(nargin[0][0]>4)b= iargs[4];
double[][] wx= new double[0][0];
if(nargin[0][0]>5)wx= iargs[5];
double[][] wy= new double[0][0];
if(nargin[0][0]>6)wy= iargs[6];
double[][] phix= new double[0][0];
if(nargin[0][0]>7)phix= iargs[7];
double[][] phiy= new double[0][0];
if(nargin[0][0]>8)phiy= iargs[8];
double[][] cx= new double[0][0];
if(nargin[0][0]>9)cx= iargs[9];
double[][] cy= new double[0][0];
if(nargin[0][0]>10)cy= iargs[10];
double[][] X0= new double[0][0];
if(nargin[0][0]>11)X0= iargs[11];
double[][] Y0= new double[0][0];
if(nargin[0][0]>12)Y0= iargs[12];
double[][] nX= new double[0][0];
double[][] nY= new double[0][0];
double[][] signalx= new double[0][0];
double[][] signaly= new double[0][0];
double[][] tanB= new double[0][0];
double[][] psi= new double[0][0];
double[][] accx= new double[0][0];
double[][] accy= new double[0][0];
double[][] B= new double[0][0];
double[][] WY= new double[0][0];
double[][] PY= new double[0][0];
double[][] nb_points = new double[0][0];
double[][] ky = new double[0][0];
double[][] kx = new double[0][0];
double[][] i = new double[0][0];
double[][] offx = new double[0][0];
double[][] offy = new double[0][0];
double[][] timestep = new double[0][0];
double[][] nx = new double[0][0];
double[][] ny = new double[0][0];
double[][] phi = new double[0][0];
label:do{
nb_points=numel(null,new double[][][]{sampling});
signaly=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
signalx=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
nX=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
nY=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
accx=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
accy=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
B=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
WY=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
PY=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
nX = subsasgn(nX,new double[][][]{matrixFromDouble(1)},X0);
nY = subsasgn(nY,new double[][][]{matrixFromDouble(1)},Y0);
tanB=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
psi=zeros(null,new double[][][]{nb_points,matrixFromDouble(1)});
ky=matrixFromDouble(1);
kx=matrixFromDouble(1);
i=matrixFromDouble(1);
offx=add(X0,mtimes(mrdivide(subsref(a,new double[][][]{matrixFromDouble(1)}),subsref(wx,new double[][][]{matrixFromDouble(1)})),cos(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{matrixFromDouble(1)}),matrixFromDouble(0)),subsref(phix,new double[][][]{matrixFromDouble(1)}))})));
offy=add(Y0,mtimes(mrdivide(subsref(b,new double[][][]{matrixFromDouble(1)}),subsref(wy,new double[][][]{matrixFromDouble(1)})),cos(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{matrixFromDouble(1)}),matrixFromDouble(0)),subsref(phiy,new double[][][]{matrixFromDouble(1)}))})));
double[][] fortemp0 =sampling;
 for(int posdfo=0;posdfo<fortemp0.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp0[0].length;sdfgsdfgdf++){ 
double[][] t = matrixFromDouble(fortemp0[posdfo][sdfgsdfgdf]);
if(any((land(lt(ky,numel(null,new double[][][]{b})),gt(t,subsref(t0ys,new double[][][]{add(ky,matrixFromDouble(1))})))))){ky=add(ky,matrixFromDouble(1));
offy=minus(add(subsref(nY,new double[][][]{minus(i,matrixFromDouble(1))}),mtimes(mrdivide(subsref(b,new double[][][]{ky}),subsref(wy,new double[][][]{ky})),cos(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{ky}),subsref(sampling,new double[][][]{i})),subsref(phiy,new double[][][]{ky}))}))),mtimes(cy,subsref(t0ys,new double[][][]{ky})));
};
if(any((land(lt(kx,numel(null,new double[][][]{a})),gt(t,subsref(t0xs,new double[][][]{add(kx,matrixFromDouble(1))})))))){kx=add(kx,matrixFromDouble(1));
offx=minus(add(subsref(nX,new double[][][]{minus(i,matrixFromDouble(1))}),mtimes(mrdivide(subsref(a,new double[][][]{kx}),subsref(wx,new double[][][]{kx})),cos(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{kx}),subsref(sampling,new double[][][]{i})),subsref(phix,new double[][][]{kx}))}))),mtimes(cx,subsref(t0xs,new double[][][]{kx})));
};
B = subsasgn(B,new double[][][]{i},subsref(b,new double[][][]{ky}));
WY = subsasgn(WY,new double[][][]{i},subsref(wy,new double[][][]{ky}));
PY = subsasgn(PY,new double[][][]{i},subsref(phiy,new double[][][]{ky}));
signaly = subsasgn(signaly,new double[][][]{i},mtimes(subsref(b,new double[][][]{ky}),sin(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{ky}),t),subsref(phiy,new double[][][]{ky}))})));
signalx = subsasgn(signalx,new double[][][]{i},mtimes(subsref(a,new double[][][]{kx}),sin(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{kx}),t),subsref(phix,new double[][][]{kx}))})));
accy = subsasgn(accy,new double[][][]{i},mtimes(mtimes(subsref(b,new double[][][]{ky}),subsref(wy,new double[][][]{ky})),cos(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{ky}),t),subsref(phiy,new double[][][]{ky}))})));
accx = subsasgn(accx,new double[][][]{i},mtimes(mtimes(subsref(a,new double[][][]{kx}),subsref(wy,new double[][][]{ky})),cos(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{kx}),t),subsref(phix,new double[][][]{kx}))})));
nX = subsasgn(nX,new double[][][]{i},add(add(mtimes(mrdivide(uminus(subsref(a,new double[][][]{kx})),subsref(wx,new double[][][]{kx})),cos(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{kx}),t),subsref(phix,new double[][][]{kx}))})),mtimes(cx,t)),offx));
nY = subsasgn(nY,new double[][][]{i},add(add(mtimes(mrdivide(uminus(subsref(b,new double[][][]{ky})),subsref(wy,new double[][][]{ky})),cos(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{ky}),t),subsref(phiy,new double[][][]{ky}))})),mtimes(cy,t)),offy));
if(any((gt(i,matrixFromDouble(1))))){timestep=(minus(subsref(sampling,new double[][][]{i}),subsref(sampling,new double[][][]{minus(i,matrixFromDouble(1))})));
nx=add(subsref(nX,new double[][][]{minus(i,matrixFromDouble(1))}),mtimes((add(mtimes(subsref(a,new double[][][]{kx}),sin(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{kx}),t),subsref(phix,new double[][][]{kx}))})),cx)),timestep));
ny=add(subsref(nY,new double[][][]{minus(i,matrixFromDouble(1))}),mtimes((add(mtimes(subsref(b,new double[][][]{ky}),sin(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{ky}),t),subsref(phiy,new double[][][]{ky}))})),cy)),timestep));
nX = subsasgn(nX,new double[][][]{i},nx);
nY = subsasgn(nY,new double[][][]{i},ny);
};
phi=minus(subsref(phix,new double[][][]{kx}),subsref(phiy,new double[][][]{ky}));
psi = subsasgn(psi,new double[][][]{i},mtimes(uminus(subsref(a,new double[][][]{kx})),sin(null,new double[][][]{phi})));
i=add(i,matrixFromDouble(1));
}
}
;
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=nX;
if(oargs.length>1)oargs[1].val=nY;
if(oargs.length>2)oargs[2].val=signalx;
if(oargs.length>3)oargs[3].val=signaly;
if(oargs.length>4)oargs[4].val=tanB;
if(oargs.length>5)oargs[5].val=psi;
if(oargs.length>6)oargs[6].val=accx;
if(oargs.length>7)oargs[7].val=accy;
if(oargs.length>8)oargs[8].val=B;
if(oargs.length>9)oargs[9].val=WY;
if(oargs.length>10)oargs[10].val=PY;
}
return nX;
}

public static double[][] cos(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] Y= new double[0][0];
label:do{
Y=sin(null,new double[][][]{add(X,mrdivide(pi,matrixFromDouble(2)))});
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=Y;
}
return Y;
}

}
