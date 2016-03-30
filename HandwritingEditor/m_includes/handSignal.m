function R = handSignal(T,X,time,ampli)

[time,ia] = unique(time);
ampli = ampli(ia);

binf = min(time);
bsup = max(time);

I = find(binf <= T & bsup>= T);
iampli = interp1(time,ampli,T(I),'splite');
        
        
R =X;
R(I) = iampli;

end