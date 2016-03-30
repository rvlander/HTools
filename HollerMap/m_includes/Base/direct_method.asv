function [a,b,wx,wy,phix,phiy] = direct_method(dXsdT,dYsdT,t0xs,t0ys,ind0xs,ind0ys)


%calcul de R/a
magic = (1/2)*(4+sqrt(2)*sqrt(-8+pi^2))/pi;
% magic = 1



% calculs des parametres
nb_arcs = size(t0ys,2)-1;

%pour y
wy = pi./diff(t0ys);
phiy = mod(-wy.*t0ys(1:nb_arcs),2*pi);

%t1s = zeros(nb_arcs,1);
b = zeros(nb_arcs,1);


for i=1:nb_arcs
    %f = cos(nT(ind0s(i):ind0s(i+1))*wy(i) + phiy(i));
    %hold on;
    %plot(nT(ind0s(i):ind0s(i+1)),0,'y');
    %[ind1,t1s,y1s] = crossing(diff(dYsdT(ind0s(i):ind0s(i+1))),nT(ind0s(i):ind0s(i+1)-1)) 
    %plot(t1s,y1s,'c*');
    %plot(nT(ind0s(i):ind0s(i+1)-1),diff(dYsdT(ind0s(i):ind0s(i+1))),'c');
    [~,mid] = max(abs(dYsdT(ind0ys(i):ind0ys(i+1))));
    val = mean(abs(dYsdT(ind0ys(i):ind0ys(i+1))));
    sval = std(abs(dYsdT(ind0ys(i):ind0ys(i+1))));
    b(i) = sign( dYsdT(ind0ys(i)+mid-1))*(val+sval)/magic;
    
    sig = sign(b(i) *sin(wy(i) * (t0ys(i) + 20) + phiy(i)));
%     lind = (ind0ys(i)+ind0ys(i+1)) / 2;
%     lind = floor(lind);
    sig2 = sign(mean(dYsdT(ind0ys(i):ind0ys(i+1))));
    if (sig == -sig2) 
        b(i) = -b(i);
    end
    
    
end


% calculs des parametres

nb_arcs = size(t0xs,2)-1;
%pour x
wx = pi./diff(t0xs);
phix = mod(-wx.*t0xs(1:nb_arcs),2*pi);
%t1s = zeros(nb_arcs,1);
a = zeros(nb_arcs,1);


for i=1:nb_arcs
    %f = cos(nT(ind0s(i):ind0s(i+1))*wy(i) + phiy(i));
    %hold on;
    %plot(nT(ind0s(i):ind0s(i+1)),0,'y');
    %[ind1,t1s,y1s] = crossing(diff(dYsdT(ind0s(i):ind0s(i+1))),nT(ind0s(i):ind0s(i+1)-1)) 
    %plot(t1s,y1s,'c*');
    %plot(nT(ind0s(i):ind0s(i+1)-1),diff(dYsdT(ind0s(i):ind0s(i+1))),'c');
    [~,mid] = max(abs(dXsdT(ind0xs(i):ind0xs(i+1))));
    val = mean(abs(dXsdT(ind0xs(i):ind0xs(i+1))));
    sval = std(abs(dXsdT(ind0xs(i):ind0xs(i+1))));
    a(i) = sign( dXsdT(ind0xs(i)+mid-1))*(val+sval)/magic;
    
    sig = sign(a(i) *sin(wx(i) * (t0xs(i) + 20) + phix(i)));
%     lind = (ind0xs(i)+ind0xs(i + 1)) / 2;
%     lind = floor(lind);
    sig2 = sign(mean(dXsdT(ind0xs(i):ind0xs(i+1))));
    if (sig == -sig2) 
        a(i) = -a(i);
    end
end


%correct a signs
for i=1:numel(a)
    if(a(i)<0)
        phix(i) = phix(i)-pi;
        a(i) = -a(i);
    end
end

for i=1:numel(b)
    if(b(i)<0)
        phiy(i) = phiy(i)-pi;
        b(i) = -b(i);
    end
end

phix = mod(phix,2*pi);
phiy = mod(phiy,2*pi);


end








