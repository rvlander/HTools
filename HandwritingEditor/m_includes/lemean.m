function R = lemean(T,mask)
I = find(mask);
R = mean(T(I)); 
end