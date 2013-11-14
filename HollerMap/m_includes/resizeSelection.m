function R=resizeSelection(sampling,mask,ot,rt)
R = zeros(size(mask));
intervs = calcul_interv(sampling,mask);
[m,n] = size(intervs);
for i=1:m

   R = R | (sampling > (ot+ (intervs(i,1)-ot)*rt) & sampling < (ot + (intervs(i,2)-ot)*rt));     
        
end

R = double(R);

end

