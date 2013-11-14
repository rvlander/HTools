
clear all;
close all;


[inds,T,X,Y] = readTR_sep('gaetan_dessin');

N = numel(inds);

for i=26:N-1
    
    try
    
    I = inds(i):inds(i+1)-1;
    
     [nX,nY] = quickAnal(T(I),X(I),Y(I),0,0);
    
    figure(1)
    subplot(1,2,1)
    hold on
    plot(X(I),Y(I),'b','LineWidth',2);
    subplot(1,2,2)
    hold on;
    plot(nX,nY,'r','LineWidth',2);
    
    catch
    end
    
end