function [R,w] = resizeHollerTimes(sampling,mask,t0s,ot,rt)

indexes = selectHollerPoints(sampling,mask,t0s);

R = t0s;
R(indexes) = ot +(R(indexes)-ot)*rt;

w = pi./diff(R);
end