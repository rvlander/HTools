function [ phirel ] = hilbert_prel(S1, S2)
%HILBERT_PREL Summary of this function goes here
%   Detailed explanation goes here

iS1 = imag(hilbert(S1));
iS2 = imag(hilbert(S2));

phirel = atan((iS1.*S2-S1.*iS2)./(S1.*S2+iS1.*iS2));

end

