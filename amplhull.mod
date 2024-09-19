#	DATI

param nv 	;				# numero di vertici che compongono il poligono (in ordine)
set V 		:= {0..nv-1};	# set dei vertici del poligono
param xv 	{V}; 			# coordinate dei vertici
param yv 	{V};

param np 	;	 			# numero di punti inammissibili
set P 		:= {0..np-1} ;	# set dei punti iinammissibili
param xp 	{P};			# coordinate
param yp 	{P};

param nr 	:= 4; 			# numero di iperpiani da utilizzare
set R 		:= {0..nr-1} ; 	# iperpiani (rette) separatori
 
#	VARIABILI	###################################################################################

var a {R};					# coefficienti degli iperpiani
var b {R};
var c {R};

var left_to {P, R} binary;	# 1 se il corrispettivo punto si trova alla sinistra della retta
var assign {P, R} binary;	# assegna ogni punto ad una sola retta

#	VINCOLI	###################################################################################

# vincolo delle rette non degeneri 
subject to nonDegeneri {r in R}:
	a[r]^2 + b[r]^2 = 1
;

# quando un punto si trova a sinistra di una retta
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

# per ogni segmento del politopo nessun iperpiano(retta) deve intersecarlo
subject to outside {r in R, v in V} :
	(a[r] * xv[v]) + (b[r]* yv[v]) + (c[r]) *
	(a[r] * xv[(v+1) mod nv]) + (b[r]* yv[(v+1) mod nv]) + (c[r]) >= 0	 
	# se sono concordi allora la retta non passa per il segmento
;	

#	OBIETTIVO	###################################################################################

# massimizzazione dei punti assegnati alle rette
# siccome ogni punto puo' essere assegnato al massimo ad una retta
# equivale a calcolare il numero totale di punti esclusi
maximize punti_esclusi:
	sum{p in P, r in R} assign[p,r]
;
	
end;