function [indexes,mask] = selectHollerPoints(sampling,selection,t0s)

intervs = calcul_interv(sampling,selection);

n = size(intervs,1);

indexes =[];



for i=1:n
    indexes = [indexes; find(intervs(i,1)<=t0s & t0s <= intervs(i,2))];
    
end

mask = zeros(size(t0s));
mask(indexes)=1;
    


end

