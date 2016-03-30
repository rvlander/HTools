function nA = resizeHollerAmplitudes(sampling,mask,t0s,a,ox,rx)

indexes = selectHollerPoints(sampling,mask,t0s);
nA=a;
nA(indexes) = ox + (a(indexes)-ox)*rx;


end

