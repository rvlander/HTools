function [R,w] = moveHollerPoints(sampling,selection,t0s,dt)
%MOVEHOLLERPOINTS Summary of this function goes here
%   Detailed explanation goes here

indexes = selectHollerPoints(sampling,selection,t0s);

R= t0s;
R(indexes) = R(indexes) +dt;

R = sort(R);

w = pi./diff(R);

end

