function [nX,nY,signalx,signaly] = resample_hw_sin_unknowns(sampling,a,b,wx,wy,phix,phiy,c,X0,Y0)


%sampling


nb_points = numel(sampling);

%signal interpol�
signaly = zeros(nb_points,1);
signalx = zeros(nb_points,1);

nX = zeros(nb_points,1);
nY = zeros(nb_points,1);

% accx = zeros(nb_points,1);
% accy = zeros(nb_points,1);

nX(1) = X0;
nY(1) = Y0;


% tanB = zeros(nb_points,1);
% psi = zeros(nb_points,1);

ky=1;
kx=1;
dx=1;
dy=1;
i=1;
offx=X0+a(1)/wx(1)*cos(wx(1)*0+phix(1));
offy=Y0+b(1)/wy(1)*cos(wy(1)*0+phiy(1));
for t=sampling
    if (ky< numel(b) && i>1 &&i<=numel(sampling)   &&signaly(i-1)*b(ky)*sin(wy(ky)*sampling(i)+phiy(ky))<0)
        ky = ky+1;
        offy=nY(i-1)+b(ky)/wy(ky)*cos(wy(ky)*(t+10)+phiy(ky));
        dx=1;
    end
    if (kx< numel(a) && i>1 && i<=numel(sampling) &&signalx(i-1)*a(kx)*sin(wx(kx)*sampling(i)+phix(kx))<0)
        kx = kx+1;
        offx=nX(i-1)+a(kx)/wx(kx)*cos(wx(kx)*(t+10)+phix(kx))-c*t;
        dx=1;
    end
    
    
    signaly(i) = b(ky)*sin(wy(ky)*t+phiy(ky));
    signalx(i) = a(kx)*sin(wx(kx)*t+phix(kx));
    
%     accy(i) = b(ky)*wy(ky)*cos(wy(ky)*t+phiy(ky));
%     accx(i) = a(kx)*wy(ky)*cos(wx(kx)*t+phix(kx));
    
    nX(i)= -a(kx)/wx(kx) * cos(wx(kx) * t + phix(kx)) + c*t +offx;
    nY(i) = -b(ky)/wy(ky) * cos(wy(ky) * t + phiy(ky)) +offy;
    
    
        if(i>1)
        timestep = (sampling(i)-sampling(i-1));
        nx =  nX(i-1) + (a(kx) * sin(wx(kx) * t + phix(kx)) + c) * timestep;
        ny = nY(i-1) + (b(ky) * sin(wy(ky) * t + phiy(ky))) * timestep;
        nX(i) = nx;
        nY(i) = ny;
        end
    
    phi = phix(kx)-phiy(ky);
    %tanB(i) = b(ky)/(a(kx)*phi);
%     psi(i) = -a(kx)*sin(phi);
    %     sphiy(i) = sphiy(k);
    i=i+1;
    dx=dx+1;
    dy=dy+1;
end

%signal interpol�


% k=1;
% i=1;
% for t=sampling
%     if (t>t0xs(kx+1)&& kx< numel(a))
%         kx = kx+1;
%     end
%
%     signalx(i) = a(kx)*sin(wx(kx)*t+phix(kx));
%     sphix(i) = phix(k);
%     i=i+1;
% end

%hold on;
%plot(sampling,signalx,'c');



%constructions
% for i=2:nb_points
% %     nX(i) = (signalx(i-1)+c)*20 + nX(i-1);
% %     nY(i) = signaly(i-1)*20 + nY(i-1);
% %     samp = sampling(1:i);
% %     sigx = signalx(1:i);
% %     sigy = signaly(1:i);
%
%
%     timestep = (sampling(i)-sampling(i-1));
%
%      if (ky< numel(b) && t>t0ys(ky+1))
%         ky = ky+1;
%     end
%     if (kx< numel(a) && t>t0xs(kx+1))
%          kx = kx+1;
%      end
%     nx = trapz(sigx+c)*timestep+nX(1);
%     ny = trapz(sigy)*timestep+nY(1);
%     nX(i) = nx;
%     nY(i) = ny;
% end

