function [nX,nY,signalx,signaly,accx,accy,ind0xs,ind0ys,cx,cy,b,w,p] = quickAnal(T,X,Y,cxbool,cybool)


T1 =T;
T2 = T;

X1 =X;
Y1 =Y;

[nT,dXsdT,dYsdT,ind0xs,t0xs,x0s,ind0ys,t0ys,y0s,cx,cy,fdY] = init(T,X,Y,cxbool,cybool);


[a,b,wx,wy,phix,phiy] = direct_method(dXsdT,dYsdT,t0xs,t0ys,ind0xs,ind0ys);
sampling = t0ys(1):20:t0ys(end);

%export2strace(a,b,wx,wy,phix,phiy,t0xs(1:end-1),t0ys(1:end-1),T(end));

accx=0;
accy=0;
[nX,nY,signalx,signaly,slant,psi,accx,accy,b,w,p] = resample_hw_sin(T',t0xs,t0ys,a,b,wx,wy,phix,phiy,cx,cy,X(1),Y(1));

T2=T;
X2=nX;
Y2=nY;

signalx = signalx +cx;
signaly = signaly +cy;
% figure(1)
% plot(X,Y,'b');
% hold on;
% plot(nX,nY,'r');
% 
% 
% figure(2)
% plot(nT,dXsdT,'LineWidth',1, 'LineSmoothing','on');
% hold on;
% plot(t0xs,x0s,'g*');
% hold on;
% plot(sampling,signalx,'c','LineWidth',1, 'LineSmoothing','on');
% hold on;
% plot(nT,filtfilt(ones(1,2)/2,1,dXsdT),'r','LineWidth',1, 'LineSmoothing','on');
% 
% 
% figure(3);
% plot(nT,dYsdT,'r','LineWidth',1, 'LineSmoothing','on');
% hold on;
% plot(sampling,signaly,'m','LineWidth',1, 'LineSmoothing','on');
% 
% 
% figure(4)
% plot(T,X);
% 
% figure(5)
% plot(T,Y);










