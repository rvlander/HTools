function  [phi]=hilb2(data)


S2=detrend(data(:,1));
S1=detrend(data(:,2));

s1=imag(hilbert(S1));
s2=imag(hilbert(S2));

phi=(atan2(s2.*S1-S2.*s1,S2.*S1+s2.*s1)).*(180./pi);

