function [nT,dXsdT,dYsdT,ind0xs,t0xs,x0s,ind0ys,t0ys,y0s,cx,cy,filtereddYsdT] = init(T,X,Y,cxbool,cybool)


T=T(:);
X=X(:);
Y=Y(:);

%calcul if c
n = size(T,1);
cx=0; 
cy=0;
if cxbool
  cx=(X(n)-X(1))/(T(n)-T(1));
end
if cybool
cy = (Y(n)-Y(1))/(T(n)-T(1));
end

% X = smooth(X,11);
% Y = smooth(X,11);

%derives

dT = diff(T);
dX = diff(X);
dY = diff(Y);

dXsdT = dX./dT-cx;
dYsdT = dY./dT-cy;

%new T
aT = T(1:n-1)+T(2:n);
nT = aT/2;

% rajout de zeros � dXsdT,dYsDT et valeur � aT
 dXsdT=[0;dXsdT;0];
 dYsdT=[0;dYsdT;0];
% 
% dXsdT = filtfilt(ones(1,6)/6,1,dXsdT);
% dYsdT = filtfilt(ones(1,6)/6,1,dYsdT);
% 
 nT = [T(1);nT;T(end)];

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

