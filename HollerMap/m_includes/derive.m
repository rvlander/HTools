function V = derive(T,X)

dT = diff(T);

aT = T(1:end-1)+T(2:end);
nT = aT/2;

dX = diff(X)./dT;
% dY = diff(Y)./dT;

V = interp1(nT,dX,T,'spline');
% dY = interp1(nT,dY,T,'spline');

end