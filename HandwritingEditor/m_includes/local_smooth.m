function R = local_smooth(T,mask,X,sm)
I = find(mask);
R = X;
R(I) = smooth(T(I),X(I),sm); 
end