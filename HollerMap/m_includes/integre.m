function X = integre(T,V,X0)

X = zeros(numel(T),1);
X(1) = X0        ;
for i=2:numel(T);
X(i) = X(i-1)+V(i-1)*(T(i)-T(i-1));
end
end