# Sperimentazione in JAVA

Questa repository contiene sperimentazioni per la ricerca del guscio convesso e delle sue approssimazioni utilizzando il linguaggio Java.

## Algoritmo di Ricerca del Convex Hull

Attualmente, è stato implementato l'algoritmo di ricerca del guscio convesso noto come **Jarvis March** o Gift Wrapping. 

## Ipotesi di Algoritmo (1)

Partendo dalla conoscenza di un guscio convesso, viene determinata un'euristica che fornisce il punto che determina il baricentro del guscio. L'algoritmo sfrutta il baricentro per ricavare successivamente gli n lati che costituiranno l'approssimazione.

## Ipotesi di Algoritmo (2)

Una seconda ipotesi per l'approssimazione del guscio convesso con un numero limitato di lati è 
implementata in [CuttingNodes](../../../tesi2/ConvexHull/src/heuristics/CuttingNodes.java). Questo algoritmo prevede la classificazione 
e lo scarto dei vertici che creano gli angoli interni più acuti nel poliedro.
<div style="display: flex; justify-content: space-between;">
    <img src="resources/CuttingNodes/10-5.png" alt="Image 1" width="30%">
    <img src="resources/CuttingNodes/30-5.png" alt="Image 2" width="30%">
    <img src="resources/CuttingNodes/50-5.png" alt="Image 3" width="30%">
</div>

## Ipotesi di Algoritmo (3)

Una terza ipotesi per l'approssimazione del guscio convesso con un numero limitato di lati è
una diretta evoluzione della precedente. il miglioramento prevede il prolungamento 
dei lati vicini al nodo rimosso, questo avviene tenendo conto del baricentro del triangolo creato
dallo stesso.
<div style="display: flex; justify-content: space-between;">
    <img src="resources/CuttingNodes2/10-5.png" alt="Image 1" width="30%">
    <img src="resources/CuttingNodes2/30-5.png" alt="Image 2" width="30%">
    <img src="resources/CuttingNodes2/50-5.png" alt="Image 3" width="30%">
</div>

## Ipotesi di Algoritmo (4)

l'esatto opposto del precedente scelgo gli angoli piu grandi
<div style="display: flex; justify-content: space-between;">
    <img src="resources/CuttingNodes3/10-5.png" alt="Image 1" width="30%">
    <img src="resources/CuttingNodes3/30-5.png" alt="Image 2" width="30%">
    <img src="resources/CuttingNodes3/50-5.png" alt="Image 3" width="30%">
</div>