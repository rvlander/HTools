function [] = export2strace(a,b,wx,wy,phix,phiy,tx,ty,tfin)

%%%%%%%%%%%%%%%%%%%%%%%%%%
fid=fopen('../straces/test.csv','wt');

Vx = [a';wx;phix;tx];
nx = size(Vx,2);
Vy = [b';wy;phiy;ty];
ny = size(Vy,2);
fprintf(fid,'%f\n',tfin);
fprintf(fid,'%d\n',nx);
fprintf(fid,'a wx phix tx\n');
fprintf(fid,'%f %f %f %f\n',Vx);

fprintf(fid,'%d\n',ny);
fprintf(fid,'b wy phiy ty\n');
fprintf(fid,'%f %f %f %f\n',Vy);



fclose(fid);

end
