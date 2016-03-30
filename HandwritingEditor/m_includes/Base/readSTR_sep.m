function [a,b,wx,wy,phix,phiy,numx,numy,t0xs,t0ys,c,startx,starty,tfinal] = readSTR_sep(filename)

    a = [];
    b = [];
    wx = [];
    wy = [];
    phix = [];
    phiy = [];
    t0xs = [];
    t0ys = [];
    c=[];
    startx=[];
    starty=[];
    tfinal=[];
    numx=[];
    numy=[];
    
    
    
    
   tline = 0;
   fid = fopen(filename);
  
    while ~feof(fid)
        %on lit le #
        tline = fgetl(fid);
        val = sscanf(tline,'#');
        
        %on lit c,tartx,tarty,tfinal
        tline = fgetl(fid);
        val = sscanf(tline,'%f,%f,%f,%f');
        c = [c; val(1)];
        startx = [startx; val(2)];
        starty = [starty; val(3)];
        tfinal = [tfinal; val(4)];
        
        %on lin le nb de x
        tline = fgetl(fid);
        val = sscanf(tline,'%d');
        numx = [numx;val(1)];
        for i=1:numx(end)
            tline = fgetl(fid);
            val = sscanf(tline,'%f,%f,%f,%f');
            t0xs = [t0xs;val(1)];
            a = [a;val(2)];
            wx = [wx;val(3)];
            phix = [phix;val(4)];
        end
        
        %on lin le nb de y
        tline = fgetl(fid);
        val = sscanf(tline,'%d');
        numy = [numy;val(1)];
        for i=1:numy(end)
            tline = fgetl(fid);
            val = sscanf(tline,'%f,%f,%f,%f');
            t0ys = [t0ys;val(1)];
            b = [b;val(2)];
            wy = [wy;val(3)];
            phiy = [phiy;val(4)];
        end
        
    end
end
