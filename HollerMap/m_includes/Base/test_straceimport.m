
clear all;
close all;

[inds,T,X,Y] = readTR_sep('trace_');
[TTa,TTb,TTwx,TTwy,TTphix,TTphiy,TTnumx,TTnumy,TTt0xs,TTt0ys,TTc,TTstartx,TTstarty,TTtfinal] = readSTR_sep('strace_');




[nT,dXsdT,dYsdT,ind0xs,t0xs,x0s,ind0ys,t0ys,y0s,c,fdY] = init(T,X,Y);
 

[a,b,wx,wy,phix,phiy] = direct_method(dXsdT,dYsdT,t0xs,t0ys,ind0xs,ind0ys);
TTsampling = t0ys(1):20:t0ys(end);
sampling = t0ys(1):20:t0ys(end);

% export2strace(a,b,wx,wy,phix,phiy,t0xs(1:end-1),t0ys(1:end-1),T(end));

[TTnX,TTnY,TTsignalx,TTsignaly,TTslant,TTpsi] = resample_hw_sin(TTsampling,TTt0xs,TTt0ys,TTa,TTb,TTwx,TTwy,TTphix,TTphiy,TTc,X(1),X(1));
[nX,nY,signalx,signaly,slant,psi] = resample_hw_sin(sampling,t0xs,t0ys,a,b,wx,wy,phix,phiy,c,X(1),X(1));


figure(1)
plot(X,Y,'b');
hold on;
plot(TTnX,-TTnY,'g');
hold on;
plot(nX,nY,'r');


figure(2)
plot(nT,dXsdT,'LineWidth',1, 'LineSmoothing','on');
hold on;
plot(t0xs,x0s,'g*');
hold on;
plot(TTt0xs,zeros(numel(TTt0xs),1),'b*');
hold on;
plot(sampling,signalx,'c','LineWidth',1, 'LineSmoothing','on');
hold on;
plot(TTsampling,TTsignalx,'r','LineWidth',1, 'LineSmoothing','on');
hold on;
%plot(nT,filtfilt(ones(1,2)/2,1,dXsdT),'r','LineWidth',1, 'LineSmoothing','on');
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










