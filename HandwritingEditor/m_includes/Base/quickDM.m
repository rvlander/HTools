function [t0xs,t0ys,a,b,wx,wy,phix,phiy,c] = quickDM(T,X,Y)
%QUICKDM Summary of this function goes here
%   Detailed explanation goes here
[nT,dXsdT,dYsdT,ind0xs,t0xs,x0s,ind0ys,t0ys,y0s,c,cy,fdY] = init(T,X,Y,1,0);
[a,b,wx,wy,phix,phiy] = direct_method(dXsdT,dYsdT,t0xs,t0ys,ind0xs,ind0ys);

end

