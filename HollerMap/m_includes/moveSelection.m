function R=moveSelection(sampling,mask,dt)
R = zeros(size(mask));
intervs = calcul_interv(sampling,mask);
[m,n] = size(intervs);
for i=1:m

   R = R | (sampling > dt+intervs(i,1) & sampling < dt+intervs(i,2));     
        
end

R = double(R);

end

