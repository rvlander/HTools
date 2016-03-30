package htools.hollermap.MCJImports;



import static org.mc.mcjavacore.MCJOperators.*;
import static org.mc.mcfunctions.MCJFunctions.*;
import org.mc.mcfunctions.MCJOutputArg;
import static org.mc.mcjavautils.MCJUtils.*;
import static org.mc.mcjavacore.MCJBaseFunctions.*;
public class direct_method{
static final double[][] pi = matrixFromDouble(Math.PI);
public static double[][] direct_method(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] dXsdT= new double[0][0];
if(nargin[0][0]>0)dXsdT= iargs[0];
double[][] dYsdT= new double[0][0];
if(nargin[0][0]>1)dYsdT= iargs[1];
double[][] t0xs= new double[0][0];
if(nargin[0][0]>2)t0xs= iargs[2];
double[][] t0ys= new double[0][0];
if(nargin[0][0]>3)t0ys= iargs[3];
double[][] ind0xs= new double[0][0];
if(nargin[0][0]>4)ind0xs= iargs[4];
double[][] ind0ys= new double[0][0];
if(nargin[0][0]>5)ind0ys= iargs[5];
double[][] a= new double[0][0];
double[][] b= new double[0][0];
double[][] wx= new double[0][0];
double[][] wy= new double[0][0];
double[][] phix= new double[0][0];
double[][] phiy= new double[0][0];
double[][] magic = new double[0][0];
double[][] nb_arcs = new double[0][0];
double[][] ignored_arg = new double[0][0];
double[][] mid = new double[0][0];
double[][] val = new double[0][0];
double[][] sval = new double[0][0];
double[][] sig = new double[0][0];
double[][] sig2 = new double[0][0];
label:do{
magic=matrixFromDouble(1);
nb_arcs=minus(numel(null,new double[][][]{t0ys}),matrixFromDouble(1));
wy=rdivide(pi,diff(null,new double[][][]{t0ys}));
phiy=mod(null,new double[][][]{times(uminus(wy),subsref(t0ys,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),nb_arcs)})),mtimes(matrixFromDouble(2),pi)});
b=zeros(null,new double[][][]{nb_arcs,matrixFromDouble(1)});
double[][] fortemp0 =colon(matrixFromDouble(1),matrixFromDouble(1),nb_arcs);
 for(int posdfo=0;posdfo<fortemp0.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp0[0].length;sdfgsdfgdf++){ 
double[][] i = matrixFromDouble(fortemp0[posdfo][sdfgsdfgdf]);
MCJOutputArg[] oa0;
oa0 = new MCJOutputArg[2];
oa0[0]= new MCJOutputArg();
oa0[1]= new MCJOutputArg();
max(oa0,new double[][][]{abs(null,new double[][][]{subsref(dYsdT,new double[][][]{colon(subsref(ind0ys,new double[][][]{i}),matrixFromDouble(1),subsref(ind0ys,new double[][][]{add(i,matrixFromDouble(1))}))})})});
ignored_arg=oa0[0].val;
mid=oa0[1].val;
val=mean(null,new double[][][]{abs(null,new double[][][]{subsref(dYsdT,new double[][][]{colon(subsref(ind0ys,new double[][][]{i}),matrixFromDouble(1),subsref(ind0ys,new double[][][]{add(i,matrixFromDouble(1))}))})})});
sval=std(null,new double[][][]{abs(null,new double[][][]{subsref(dYsdT,new double[][][]{colon(subsref(ind0ys,new double[][][]{i}),matrixFromDouble(1),subsref(ind0ys,new double[][][]{add(i,matrixFromDouble(1))}))})})});
b = subsasgn(b,new double[][][]{i},mrdivide(mtimes(sign(null,new double[][][]{subsref(dYsdT,new double[][][]{minus(add(subsref(ind0ys,new double[][][]{i}),mid),matrixFromDouble(1))})}),(add(val,sval))),magic));
sig=sign(null,new double[][][]{mtimes(subsref(b,new double[][][]{i}),sin(null,new double[][][]{add(mtimes(subsref(wy,new double[][][]{i}),(add(subsref(t0ys,new double[][][]{i}),matrixFromDouble(20)))),subsref(phiy,new double[][][]{i}))}))});
sig2=sign(null,new double[][][]{mean(null,new double[][][]{subsref(dYsdT,new double[][][]{colon(subsref(ind0ys,new double[][][]{i}),matrixFromDouble(1),subsref(ind0ys,new double[][][]{add(i,matrixFromDouble(1))}))})})});
if(any((eq(sig,uminus(sig2))))){b = subsasgn(b,new double[][][]{i},uminus(subsref(b,new double[][][]{i})));
};
}
}
;
nb_arcs=minus(numel(null,new double[][][]{t0xs}),matrixFromDouble(1));
wx=rdivide(pi,diff(null,new double[][][]{t0xs}));
phix=mod(null,new double[][][]{times(uminus(wx),subsref(t0xs,new double[][][]{colon(matrixFromDouble(1),matrixFromDouble(1),nb_arcs)})),mtimes(matrixFromDouble(2),pi)});
a=zeros(null,new double[][][]{nb_arcs,matrixFromDouble(1)});
double[][] fortemp1 =colon(matrixFromDouble(1),matrixFromDouble(1),nb_arcs);
 for(int posdfo=0;posdfo<fortemp1.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp1[0].length;sdfgsdfgdf++){ 
double[][] i = matrixFromDouble(fortemp1[posdfo][sdfgsdfgdf]);
MCJOutputArg[] oa1;
oa1 = new MCJOutputArg[2];
oa1[0]= new MCJOutputArg();
oa1[1]= new MCJOutputArg();
max(oa1,new double[][][]{abs(null,new double[][][]{subsref(dXsdT,new double[][][]{colon(subsref(ind0xs,new double[][][]{i}),matrixFromDouble(1),subsref(ind0xs,new double[][][]{add(i,matrixFromDouble(1))}))})})});
ignored_arg=oa1[0].val;
mid=oa1[1].val;
val=mean(null,new double[][][]{abs(null,new double[][][]{subsref(dXsdT,new double[][][]{colon(subsref(ind0xs,new double[][][]{i}),matrixFromDouble(1),subsref(ind0xs,new double[][][]{add(i,matrixFromDouble(1))}))})})});
sval=std(null,new double[][][]{abs(null,new double[][][]{subsref(dXsdT,new double[][][]{colon(subsref(ind0xs,new double[][][]{i}),matrixFromDouble(1),subsref(ind0xs,new double[][][]{add(i,matrixFromDouble(1))}))})})});
a = subsasgn(a,new double[][][]{i},mrdivide(mtimes(sign(null,new double[][][]{subsref(dXsdT,new double[][][]{minus(add(subsref(ind0xs,new double[][][]{i}),mid),matrixFromDouble(1))})}),(add(val,sval))),magic));
sig=sign(null,new double[][][]{mtimes(subsref(a,new double[][][]{i}),sin(null,new double[][][]{add(mtimes(subsref(wx,new double[][][]{i}),(add(subsref(t0xs,new double[][][]{i}),matrixFromDouble(20)))),subsref(phix,new double[][][]{i}))}))});
sig2=sign(null,new double[][][]{mean(null,new double[][][]{subsref(dXsdT,new double[][][]{colon(subsref(ind0xs,new double[][][]{i}),matrixFromDouble(1),subsref(ind0xs,new double[][][]{add(i,matrixFromDouble(1))}))})})});
if(any((eq(sig,uminus(sig2))))){a = subsasgn(a,new double[][][]{i},uminus(subsref(a,new double[][][]{i})));
};
}
}
;
double[][] fortemp2 =colon(matrixFromDouble(1),matrixFromDouble(1),numel(null,new double[][][]{a}));
 for(int posdfo=0;posdfo<fortemp2.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp2[0].length;sdfgsdfgdf++){ 
double[][] i = matrixFromDouble(fortemp2[posdfo][sdfgsdfgdf]);
if(any((lt(subsref(a,new double[][][]{i}),matrixFromDouble(0))))){phix = subsasgn(phix,new double[][][]{i},minus(subsref(phix,new double[][][]{i}),pi));
a = subsasgn(a,new double[][][]{i},uminus(subsref(a,new double[][][]{i})));
};
}
}
;
double[][] fortemp3 =colon(matrixFromDouble(1),matrixFromDouble(1),numel(null,new double[][][]{b}));
 for(int posdfo=0;posdfo<fortemp3.length;posdfo++){ 
 for(int sdfgsdfgdf=0;sdfgsdfgdf<fortemp3[0].length;sdfgsdfgdf++){ 
double[][] i = matrixFromDouble(fortemp3[posdfo][sdfgsdfgdf]);
if(any((lt(subsref(b,new double[][][]{i}),matrixFromDouble(0))))){phiy = subsasgn(phiy,new double[][][]{i},minus(subsref(phiy,new double[][][]{i}),pi));
b = subsasgn(b,new double[][][]{i},uminus(subsref(b,new double[][][]{i})));
};
}
}
;
phix=mod(null,new double[][][]{phix,mtimes(matrixFromDouble(2),pi)});
phiy=mod(null,new double[][][]{phiy,mtimes(matrixFromDouble(2),pi)});
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=a;
if(oargs.length>1)oargs[1].val=b;
if(oargs.length>2)oargs[2].val=wx;
if(oargs.length>3)oargs[3].val=wy;
if(oargs.length>4)oargs[4].val=phix;
if(oargs.length>5)oargs[5].val=phiy;
}
return a;
}

public static double[][] std(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] varargin= new double[0][0];
if(nargin[0][0]>0)varargin= iargs[0];
double[][] y= new double[0][0];
label:do{
y=sqrt(null,new double[][][]{var(null,iargs)});
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
}

public static double[][] var(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] x= new double[0][0];
if(nargin[0][0]>0)x= iargs[0];
double[][] w= new double[0][0];
if(nargin[0][0]>1)w= iargs[1];
double[][] dim= new double[0][0];
if(nargin[0][0]>2)dim= iargs[2];
double[][] y= new double[0][0];
double[][] n = new double[0][0];
double[][] denom = new double[0][0];
double[][] xbar = new double[0][0];
double[][] wresize = new double[0][0];
double[][] x0 = new double[0][0];
label:do{
if(any(isinteger(null,new double[][][]{x}))){error(null,new double[][][]{message(null,new double[][][]{matrixFromText("MATLAB:var:integerClass")})});
};
if(any(lor(lt(nargin,matrixFromDouble(2)),isempty(null,new double[][][]{w})))){w=matrixFromDouble(0);
};
if(any(lt(nargin,matrixFromDouble(3)))){if(any(isequal(null,new double[][][]{x,new double[0][0]}))){y=NaN(null,new double[][][]{class_reserved(null,new double[][][]{x})});
break label;
};
dim=find(null,new double[][][]{ne(size(null,new double[][][]{x}),matrixFromDouble(1)),matrixFromDouble(1)});
if(any(isempty(null,new double[][][]{dim}))){dim=matrixFromDouble(1);
};
};
n=size(null,new double[][][]{x,dim});
if(any(lor(isequal(null,new double[][][]{w,matrixFromDouble(0)}),isequal(null,new double[][][]{w,matrixFromDouble(1)})))){if(any(land(eq(w,matrixFromDouble(0)),gt(n,matrixFromDouble(1))))){denom=minus(n,matrixFromDouble(1));
}else{denom=n;
};
if(any(gt(n,matrixFromDouble(0)))){xbar=rdivide(sum(null,new double[][][]{x,dim}),n);
x=bsxfun(null,new double[][][]{matrixFromText("minus"),x,xbar});
};
y=rdivide(sum(null,new double[][][]{power(abs(null,new double[][][]{x}),matrixFromDouble(2)),dim}),denom);
}else if(all(land(isvector(null,new double[][][]{w}),all(null,new double[][][]{ge(w,matrixFromDouble(0))})))){if(any(ne(numel(null,new double[][][]{w}),n))){if(any(isscalar(null,new double[][][]{w}))){error(null,new double[][][]{message(null,new double[][][]{matrixFromText("MATLAB:var:invalidWgts")})});
}else{error(null,new double[][][]{message(null,new double[][][]{matrixFromText("MATLAB:var:invalidSizeWgts")})});
};
};
wresize=ones(null,new double[][][]{matrixFromDouble(1),max(null,new double[][][]{ndims(null,new double[][][]{x}),dim})});
wresize = subsasgn(wresize,new double[][][]{dim},n);
w=reshape(null,new double[][][]{rdivide(w,sum(null,new double[][][]{w})),wresize});
x0=bsxfun(null,new double[][][]{matrixFromText("times"),w,x});
x=bsxfun(null,new double[][][]{matrixFromText("minus"),x,sum(null,new double[][][]{x0,dim})});
y=sum(null,new double[][][]{bsxfun(null,new double[][][]{matrixFromText("times"),w,power(abs(null,new double[][][]{x}),matrixFromDouble(2))}),dim});
}else{error(null,new double[][][]{message(null,new double[][][]{matrixFromText("MATLAB:var:invalidWgts")})});
};
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

public static double[][] ndims(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] R= new double[0][0];
label:do{
R=length(null,new double[][][]{size(null,new double[][][]{X})});
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=R;
}
return R;
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

public static double[][] isscalar(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] V= new double[0][0];
if(nargin[0][0]>0)V= iargs[0];
double[][] B= new double[0][0];
double[][] T = new double[0][0];
label:do{
T=size(null,new double[][][]{V});
B=land(eq(subsref(T,new double[][][]{matrixFromDouble(1)}),matrixFromDouble(1)),eq(subsref(T,new double[][][]{matrixFromDouble(2)}),matrixFromDouble(1)));
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=B;
}
return B;
}

public static double[][] isvector(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] V= new double[0][0];
if(nargin[0][0]>0)V= iargs[0];
double[][] B= new double[0][0];
double[][] T = new double[0][0];
label:do{
T=size(null,new double[][][]{V});
B=lor(eq(subsref(T,new double[][][]{matrixFromDouble(1)}),matrixFromDouble(1)),eq(subsref(T,new double[][][]{matrixFromDouble(2)}),matrixFromDouble(1)));
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=B;
}
return B;
}

public static double[][] class_reserved(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] input_args= new double[0][0];
if(nargin[0][0]>0)input_args= iargs[0];
double[][] A= new double[0][0];
label:do{
A=matrixFromText("double");
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=A;
}
return A;
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

public static double[][] isinteger(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] X= new double[0][0];
if(nargin[0][0]>0)X= iargs[0];
double[][] res= new double[0][0];
label:do{
res=matrixFromDouble(0);
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=res;
}
return res;
}

public static double[][] mean(MCJOutputArg[] oargs,double[][][] iargs) throws Exception,Throwable{
double[][] nargin = matrixFromDouble(iargs.length);
double[][] x= new double[0][0];
if(nargin[0][0]>0)x= iargs[0];
double[][] dim= new double[0][0];
if(nargin[0][0]>1)dim= iargs[1];
double[][] y= new double[0][0];
label:do{
if(any(eq(nargin,matrixFromDouble(1)))){dim=find(null,new double[][][]{ne(size(null,new double[][][]{x}),matrixFromDouble(1)),matrixFromDouble(1)});
if(any(isempty(null,new double[][][]{dim}))){dim=matrixFromDouble(1);
};
y=mrdivide(sum(null,new double[][][]{x}),size(null,new double[][][]{x,dim}));
}else{y=mrdivide(sum(null,new double[][][]{x,dim}),size(null,new double[][][]{x,dim}));
};
}while(false);
if(oargs !=null){
if(oargs.length>0)oargs[0].val=y;
}
return y;
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
