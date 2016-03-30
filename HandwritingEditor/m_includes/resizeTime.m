function [nnX]=resizeTime(sampling,mask,X,ot,rt)
    I = find(mask);
    nX = X;
    shifted_sampling = sampling;
    shifted_sampling(I) = ot +(sampling(I)-ot)*rt;
    intervs = calcul_interv(ot+(sampling-ot)*rt,mask);
    
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

