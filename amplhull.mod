#	DATI

param nv 	;			# numero di vertici che compongono il poligono (in ordine)
set V 		:= {0..nv-1};	# set dei punti del poligono
param xv 	{V}; 		# coordinate dei vertici del poligono
param yv 	{V};

param np 	;	 		# numero di punti del sampling
set P 		:= {0..np-1} ;	# set dei punti del sampling
param xp 	{P};
param yp 	{P};

param nr 	:= 4; 		# numero di iperpiani da utilizzare
set R 		:= {0..nr-1} ; 	# iperpiani da generare
 
#	VARIABILI	###################################################################################

var a {R};				# coefficienti delle rette
var b {R};
var c {R};

var left_to {P, R} binary;
var assign {P, R} binary;	# assegna ogni punto ad una sola retta

#	VINCOLI	###################################################################################

# vincolo delle rette
subject to nonDegeneri {r in R}:
	a[r]^2 + b[r]^2 = 1
;

# definizione di quando un punto si trova a destra di una retta
subject to defineLeft_to {p in P, r in R}:
	(a[r] * xp[p]) + (b[r]* yp[r]) + (c[r]) * (-left_to[p, r]) <= 0
;


# ogni punto puo essere assegnato ad una retta solo se si trova alla sua destra
subject to at_least_right_OnePoint {p in P, r in R} :
	assign[p, r] <= left_to[p, r]
;

# ogni punto puo' essere assegnato al massimo ad una sola retta 
subject to justOnePoint {p in P} :
	sum{r in R} assign[p, r] <= 1
;

# le rette non devono intersecare i segmenti del poligono
subject to outside {r in R, v in V} :
	(a[r] * xv[v]) + (b[r]* yv[v]) + (c[r]) *
	(a[r] * xv[(v+1) mod nv]) + (b[r]* yv[(v+1) mod nv]) + (c[r]) >= 0	 
	# se sono concordi allora la retta non passa per il segmento
;	

#	OBIETTIVO	###################################################################################

# massimizzazione dei punti inammissibili tagliati fuori dalle rette
maximize punti_esclusi:
	sum{p in P, r in R} assign[p,r]
;
	

end;