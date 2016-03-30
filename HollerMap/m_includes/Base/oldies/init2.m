function [nT,dXsdT,dYsdT,ind0xs,t0xs,x0s,ind0ys,t0ys,y0s,c,filtereddYsdT] = init2(T,X,Y)
%calcul if c
n = size(T,1);
c = (X(n)-X(1))/(T(n)-T(1));
%c=0;


%derives

dT = diff(T);
dX = diff(X);
dY = diff(Y);

dXsdT = dX./dT-c;
dYsdT = dY./dT;

%new T
aT = T(1:n-1)+T(2:n);
nT = aT/2;

% rajout de zeros � dXsdT,dYsDT et valeur � aT
%  dXsdT=[0;dXsdT;0];
%  dYsdT=[0;dYsdT;0];
% 
% dXsdT = filtfilt(ones(1,6)/6,1,dXsdT);
% dYsdT = filtfilt(ones(1,6)/6,1,dYsdT);
% 
%  nT = [T(1);nT;T(end)];

filtereddXsdT = filtfilt(ones(1,6)/6,1,dXsdT);
% filtereddXsdT(1) = 0;
% filtereddXsdT(end) = 0;

filtereddYsdT = filtfilt(ones(1,6)/6,1,dYsdT);
% filtereddYsdT(1) = 0;
% filtereddYsdT(end) = 0;


% points where dY/dT crosses zero
[ind0xs,t0xs,x0s] = crossing(filtereddXsdT,nT); 
[ind0ys,t0ys,y0s] = crossing(filtereddYsdT,nT);

% [ind0xs,t0xs,x0s] = crossing(dXsdT,nT); 
% [ind0ys,t0ys,y0s] = crossing(dYsdT,nT);

end

