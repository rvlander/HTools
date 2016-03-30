function [nX]=resizeSignal(X,mask,ox,rx)
ox
rx
I = find(mask==1);
nX = X;
nX(I) = ox + (X(I)-ox)*rx;
end

