function [dX] = resample_hw_sin_nophi_onedir(sampling,t0xs,a,wx,phix0,c)


%sampling


nb_points = numel(sampling);

%signal interpol�
signalx = zeros(nb_points,1);


phix = phix0;



kx=1;
i=1;

for t=sampling
    if (kx< numel(a) && t>t0xs(kx+1))
        kx = kx+1;
        
        phix=-wx(kx)*t0xs(kx);
        if(signalx(i-1)*a(kx)*sin(wx(kx)*sampling(i)+phix)>0)
            phix=phix+pi;
        end

    end
    
    

    signalx(i) = a(kx)*sin(wx(kx)*t+phix);
    


    
    i=i+1;
end

dX = signalx;

