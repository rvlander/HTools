function [T1,X1,Y1, T2, X2, Y2,dXsdT,dYsdT,signalx,signaly] = quickAnal2(filename)

[inds,T,X,Y] = readTR_sep(filename);

T1 =T;
T2 = T;

X1 =X;
Y1 =Y;

[nT,dXsdT,dYsdT,ind0xs,t0xs,x0s,ind0ys,t0ys,y0s,c,fdY] = init2(T,X,Y);


[a,b,wx,wy,phix,phiy,ad,bd,wxd,wyd,phixd,phiyd] = direct_method2(dXsdT,dYsdT,t0xs,t0ys,ind0xs,ind0ys,T(end));
sampling = t0ys(1):20:t0ys(end);

%export2strace(a,b,wx,wy,phix,phiy,t0xs(1:end-1),t0ys(1:end-1),T(end));

[nX,nY,signalx,signaly,slant,psi] = resample_hw_sin2(T',t0xs,t0ys,a,b,wx,wy,phix,phiy,ad,bd,wxd,wyd,phixd,phiyd,c,X(1),Y(1));

T2=T;
X2=nX;
Y2=nY;


dXsdT = interp1(nT,dXsdT,T,'spline');
dYsdT = interp1(nT,dYsdT,T,'spline');

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










