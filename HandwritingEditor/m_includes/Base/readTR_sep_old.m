function [inds,T,X,Y] = readTR_sep_old(filename)
xmax =  5907/5;
ymax =  8307/5;  
stroke_changed = false;
inds = [];
    X = [];
    Y = [];
    T = [];
    tline = 0;
    fid = fopen(filename);
    ntline = 0;
    ntline=fgetl(fid);
    i = 1;
    while tline~=-1
        tline = ntline;
        ntline = fgetl(fid);
        if(tline~=-1)
            if (tline(1) == '#')
                inds=[inds;i];
                stroke_changed = true;
            else
                val = sscanf(tline,'%f,%f,%f');
                if val(2) <= xmax && val(3) <=ymax
                if(isequal(X,[]) && isequal(Y,[]) || norm([X(end) Y(end)]-[val(2) val(3)])<100 || stroke_changed)
                    T = [T;val(1)];
                    X = [X;val(2)];
                    Y = [Y;val(3)];
                    i = i+1;
                    stroke_changed = false;
                end
                end
            end
        end
    end
    Y = -Y;
    fclose(fid);
end
