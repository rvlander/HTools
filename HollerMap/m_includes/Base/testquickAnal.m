clear all;
close all;

[~,X1,X2,~,Y1,Y2] = quickAnal2('/home/rvlander/These/CoopLAPMA/Experimentations/David/C19_phrase/trace_enregistree_C19D 2');
plot(X1,X2)
hold on
plot(Y1,Y2,'r')