function [nnX]=timeShift(sampling,mask,X,dt)
    I = find(mask);
    nX = X;
    shifted_sampling = sampling;
    shifted_sampling(I) = sampling(I) +dt;
    intervs = calcul_interv(sampling+dt,mask);
    
    [i,j] = size(intervs);
    for k=1:i
        L = find(~mask & (intervs(k,1) > shifted_sampling | intervs(k,2) < shifted_sampling));
        NI = sort(sort([L I]));
        shifted_sampling = shifted_sampling(NI);
        mask = mask(NI);
        nX = nX(NI); 
        I=find(mask);
    end
    
     nnX = interp1(shifted_sampling,nX,sampling,'spline');

end

