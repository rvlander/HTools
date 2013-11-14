function [t0,na,nw] = deleteHollerPoints(sampling,selection,t0s,a,w)

indexes = selectHollerPoints(sampling,selection,t0s);

t0 = t0s;
na =a;
nw =w;
t0(indexes) = [];
na(indexes) = [];
nw(indexes) = [];

end