# Sperimentazione in Java

Questa repository contiene sperimentazioni per la ricerca del guscio convesso 
e delle sue approssimazioni utilizzando il linguaggio Java.

## Algoritmo di Ricerca del Guscio Convesso

Per la ricerca del guscio convesso dato un set di punti, è stato implementato l'algoritmo di ricerca noto 
come **Jarvis March** (o Gift Wrapping). Il guscio convesso trovato viene poi utilizzato per trovare diverse 
euristiche che consentiranno agli algoritmi di ricerca di trovare un poligono di n lati che possa approssimare con 
maggiore accuratezza possibile il guscio convesso.

## Algoritmi con euristica completa del guscio convesso
questi algoritmi presumono la completa conoscenza del guscio convesso,
procedono per semplificazione di questo tramite approssimazioni iterative.

## Algoritmi cutting nodes
gli algoritmi cutting nodes hanno alla base del loro funzionamento la selezione di un set di punti che meglio rispetta determinati criteri di selezione

#### 1. [Ipotesi di Algoritmo Cutting Smaller Angles (CSA)](src/main/java/heuristics/fromConvexHull/cuttingNodes/CuttingSmallerAngle.java)

Questo algoritmo prevede lo scarto dei vertici che creano gli angoli interni al poliedro che siano più acuti.
Si presume che gli angoli più acuti possano essere formati dai vertici che possono essere considerati outlier.

<p style="text-align: center;"><strong>Approssimazione con 5 lati</strong></p>
<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingSmallerAngles/10-5.png" alt="Image 1" style="width: 100%;">
        <p>10 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingSmallerAngles/30-5.png" alt="Image 2" style="width: 100%;">
        <p>30 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingSmallerAngles/50-5.png" alt="Image 3" style="width: 100%;">
        <p>50 punti</p>
    </div>
</div>

Le approssimazioni ottenute da questo algoritmo portano allo scarto di alcuni punti ammissibili poichè il poligono risultante sarà sempre una approssimazione per difetto dell'originale.

> **Indice di Jaccard**: ≈ 0.2

#### 2. [Ipotesi di Algoritmo Cutting Smaller Angles 2 (CSA2)](src/main/java/heuristics/fromConvexHull/cuttingNodes/CuttingSmallerAngle2.java)

Evolvendo la precedente ipotesi e consentendo all'algoritmo di poter includere i nodi esclusi traslando il taglio creato, si può cosi creare un guscio che oltre a essere convesso rispetta l'inclusione di tutti i punti ammissibili.


<div style="text-align: center; width: 100%;">
    <img src="media/demo_smaller_angle.jpg" alt="Image 1" style="width: 60%;">
        <p>1) Dimostrazione logica dell'algoritmo cutting Smaller Angles<br>
            2) Dimostrazione logica dell'algoritmo cutting Smaller Angles2</p>
</div>

<p style="text-align: center;"><strong>Approssimazione con 5 lati</strong></p>
<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingSmallerAngles2/10-5.png" alt="Image 1" style="width: 100%;">
        <p>10 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingSmallerAngles2/30-5.png" alt="Image 2" style="width: 100%;">
        <p>30 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingSmallerAngles2/50-5.png" alt="Image 3" style="width: 100%;">
        <p>50 punti</p>
    </div>
</div>

Questo approccio rende l'algoritmo precedente accettabile poichè include tutti i punti ammissibili, tuttavia all'aumentare dei vertici è molto probabile che le traslazioni portino i lati del poliedro risultante molto lontani dalla posizione ideale.

> **Indice di Jaccard**: ≈ 0.2

#### 3. [Ipotesi di Algoritmo Cutting Larger Angles (CLA)](src/main/java/heuristics/fromConvexHull/cuttingNodes/CuttingLargerAngle.java)

Applicando un cambio di ragionamento all'algoritmo CSA si potrebbe ipotizzare che gli angoli interni con ampiezza maggiore siano i candidati migliori per essere approssimati con un segmento, questo poichè la perdita di area applicando il taglio sarebbe minima.

<p style="text-align: center;"><strong>Approssimazione con 5 lati</strong></p>
<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingLargerAngles/10-5.png" alt="Image 1" style="width: 100%;">
        <p>10 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingLargerAngles/30-5.png" alt="Image 2" style="width: 100%;">
        <p>30 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingLargerAngles/50-5.png" alt="Image 3" style="width: 100%;">
        <p>50 punti</p>
    </div>
</div>

Si rivela un approccio vincente in termini di somiglianza con l'area del guscio convesso. Condivide però assieme all'approcco CSA il fatto di non includere i nodi che vengono tagliati, rendendolo così inaccettabile.

> **Indice di Jaccard**: ≈ 0.78

#### 4. [Ipotesi di Algoritmo Cutting Larger Angles 2 (CLA2)](src/main/java/heuristics/fromConvexHull/cuttingNodes/CuttingLargerAngle2.java)

Applicando la medesima trasformazione fatta nell'algoritmo CSA2, si consente al precedente algoritmo di includere tutti i punti ammissibili, rendendo di fatto il precedente algoritmo accettabile.

<div style="text-align: center; width: 100%;">
    <img src="media/demo_larger_angle.jpg" alt="Image 1" style="width: 60%;">
        <p>1) Dimostrazione logica dell'algoritmo cutting Larger Angles<br>
            2) Dimostrazione logica dell'algoritmo cutting Larger Angles2</p>
</div>

<p style="text-align: center;"><strong>Approssimazione con 5 lati</strong></p>
<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingLargerAngles2/10-5.png" alt="Image 1" style="width: 100%;">
        <p>10 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingLargerAngles2/30-5.png" alt="Image 2" style="width: 100%;">
        <p>30 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/CuttingLargerAngles2/50-5.png" alt="Image 3" style="width: 100%;">
        <p>50 punti</p>
    </div>
</div>

Il risultato ottenuto in termini di somiglianza visiva con il poligono originale è buono. L'indice di Jaccard fornito risulta rispecchiare questo punto di vista,
può essere una opzione più che valida per l'approssimazione del guscio convesso.

> **Indice di Jaccard**: ≈ 0.77

### Algoritmi con scelta di Iperpiani

A differenza degli algoritmi cutting edges, gli algoritmi a scelta di iperpiani selezionano un numero n di lati dell'involucro convesso in base a differenti criteri. 
Questi una volta prolungati forniscono una approssimazione del guscio convesso.


#### 5. [Ipotesi di Algoritmo (Less Area)](src/main/java/heuristics/fromConvexHull/edgeChoice/LessArea.java)

L'algoritmo ipotizzato per evitare la natura combinatoria del problema di scelta di n lati per l'approssimazione cerca i lati che meglio possano rappresentare un lato del poligono, selezionando quelli che tendono a minimizzare l'area compresa fra essi e il poligono stesso, come mostrato in figura.

<div style="text-align: center; width: 100%;">
    <img src="media/demo_smaller_area.jpg" alt="Image 1" style="width: 60%;">
    <p>Dimostrazione del criterio di scelta del lato migliore</p>
</div>
Approssimazione con 5 lati

<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 30%;">
        <img src="media/LessArea/10-5.png" alt="Image 1" style="width: 100%;">
        <p>10 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/LessArea/30-5.png" alt="Image 2" style="width: 100%;">
        <p>30 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/LessArea/50-5.png" alt="Image 3" style="width: 100%;">
        <p>50 punti</p>
    </div>
</div>

I poligoni generati da questo algoritmo sembrano visivamente rappresentare al meglio la forma originale del poligono da cui vengono estratti i punti per il campione.
La selezione del lato risulta essere piu' complessa all'aumentare del numero di lati del poliedro, tuttavia il processo di selezione puo' essere facilmente parallelizzato.

> **Indice di Jaccard**: ≈ 0.56

Il problema principale dell'algoritmo si presenta quando molti lati adiacenti del guscio convesso risultano essere i migliori. In quel caso, il poligono generato non solo può erroneamente escludere tutti i punti positivi, ma non rappresenta nemmeno la forma del poligono originale.


<h2 style="page-break-before: always;" id="algoritmi-su-euristica-puntiforme">Algoritmi su Euristica Puntiforme</h2>

Questi algoritmi si basano su una euristica puntiforme che fornisce meno informazioni riguardo alla forma e alla disposizione dei punti nel piano. Gli algoritmi presentati utilizzano come euristica il punto che rappresenta il centro di massa del guscio convesso (questo, in caso di distribuzione omogenea, potrebbe essere approssimato come il baricentro dell'intero set di punti ammissibili).
 
#### 1. [Ipotesi di Algoritmo (Distance From G)](src/main/java/heuristics/fromPoints/DistanceFromG.java)

Questo algoritmo prevede la ricerca dei nodi più distanti dal baricentro del guscio convesso. Ad ogni iterazione viene aggiunto un nuovo vertice e vengono rimossi i precedenti vertici che, con l'aggiunta di quest'ultimo, vengono inclusi nel poliedro risultante.

<div style="text-align: center; width: 100%;">
    <img src="media/demo_distance_g.jpg" alt="Image 1" style="width: 50%;">
    <p>Dimostrazione del criterio di scelta del vertice migliore</p>
</div>

<p style="text-align: center;"><strong>Approssimazione con 5 lati</strong></p>
<div style="display: flex; justify-content: space-between;">
    <div style="text-align: center; width: 30%;">
        <img src="media/DistanceFromG/10-5.png" alt="Image 1" style="width: 100%;">
        <p>10 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/DistanceFromG/30-5.png" alt="Image 2" style="width: 100%;">
        <p>30 punti</p>
    </div>
    <div style="text-align: center; width: 30%;">
        <img src="media/DistanceFromG/50-5.png" alt="Image 3" style="width: 100%;">
        <p>50 punti</p>
    </div>
</div>

L'algoritmo risulta particolarmente efficace quando i punti sono distribuiti in maniera omogenea. Al contrario, quando i punti si concentrano lungo due poli o presentano una densità elevata attorno a un punto, l'approssimazione risulterà lontana dal reale convex hull.
Inoltre come CLA CSA ottiene una approssimazione del guscio convesso per difetto il che lo rende non corretto.

> **Indice di Jaccard**: ≈ 0.32


# Testing
per ottenere l'indice di ogni 
Il testing è stato effettuato utilizzando un approccio iterativo. 
Una volta scelto un poligono di riferimento, vengono prelevati 100 campioni di n punti randomici al suo interno. Questo procedimento viene ripetuto per n = 10, 50, 100 per analizzare se la precisione aumenta all'aumentare del numero di punti. I poligoni generati dalle euristiche vengono infine paragonati con il guscio convesso generato dall'algoritmo JarvisMarch. Della serie di indici generati viene fatta la media per ciascuna euristica.

### Risultati Jaccard Index e Tempi di Esecuzione

| Algoritmo                 | Jaccard Index | Tempo Medio (μs) | Eccezioni |
|---------------------------|---------------|------------------|-----------|
| CuttingSmallerAngle       | 0.142         | 47               | null      |
| CuttingSmallerAngle2      | 0.141         | 67               | 50        |
| CuttingLargerAngle        | 0.780         | 67               | null      |
| CuttingLargerAngle2       | 0.768         | 58               | null      |
| DistanceFromG             | 0.321         | 102              | null      |
| LessArea                  | 0.463         | 731              | 47        |