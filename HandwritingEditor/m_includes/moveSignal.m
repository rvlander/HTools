function [nX]=moveSignal(X,mask,dy)
I = find(mask==1);
nX = X;
nX(I) = X(I) +dy;
end

