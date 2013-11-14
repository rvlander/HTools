function [ intervals ] = calcul_interv( growing_signal,mask )

current =0;
n = numel(growing_signal);
intervals = [];
b=[];

for i=1:n
    if current
        if ~mask(i)
            intervals = [intervals; b growing_signal(i)];
        end
    else
        if mask(i)
            b = growing_signal(i);
        end
    end
    current = mask(i);
end
if(current)
    intervals = [intervals; b growing_signal(end)];
end
end

