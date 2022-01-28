# POMPA INSULINICA
#### Ludovico Peruzzi VR445780

Sistema integrato in una pompa insulinica usata dai pazienti diabetici per tenere controllato il livello di glucosio nel sangue.
Il sistema si occupa di aiutare le persone affette da **diabete di tipo 1**.

## REQUISITI
### 1.Sensore

Il sistema deve raccogliere dati da un **sensore** il quale deve **controllare il livello di zucchero nel sangue**. Il livello di glucosio del sangue deve stare **all'interno di un range di valori** e mai uscire da tale range poichè questo comporterebbe un **grave pericolo per il paziente**.
Di seguito vengono mostrati i valori di glucosio corretti, alti e bassi.

| *Valori glicemici normali* | *Condizione* |
| --- | ---|
|  70 - 125 mg/dL| A digiuno e prima dei pasti  |
| <= 160-180 mg/dL | Due ore dopo i pasti | 

| *Valori glicemici alterati* | *Condizione* |
| --- | ---|
| < 70 mg/dL| Ipoglicemia |
| \> 125 mg/dL | Intolleranza al glucosio (a digiuno). Cosiderato già diabete |
| \> 200 mg/dL | Diabete (sia a digiuno che dopo i pasti)|

Il sensore deve comunicare con il sistema per fornire i dati così da poter valutare la situazione e prendere le corrette decisioni. La comunicazione avviene tramite Bluetooth con il sistema e deve essere sempre costante e non subire perdite di dati.
Il sensore deve essere revisionato da un tecnico ogni 6 mesi, Per questo il sistema deve fornire un messaggio all'utente per ricordargli il controllo.

### 2.Pompa

Si occupa dell' **iniezione dell' insulina** nei dosaggi corretti quando riceve il comando dal sistema. L'insulina si misura in UI(unità internazionale). La pompa ha una capacità di **350 UI** e deve iniettare **dosi comprese tra i 2 UI e i 16 UI** in base ai valori glicemici del paziente.

| *Valore glicemico* | *UI di insulina* |
| --- | --- |
| \> 500 mg/dL | 16 UI ( + allarme) |
| 450 - 500 mg/dL | 14 UI |
| 400 - 450 mg/dL | 12 UI |
| 350 - 400 mg/dL | 10 UI |
| 300 - 350 mg/dL | 8 UI |
| 250 - 200 mg/dL | 6 UI |
| 200 - 250 mg/dL | 4 UI |
| 126- 200 mg/dL  | 2 UI |
| 70 - 126 mg/dL | 0 UI|
| < 70 mg/dL | 0 UI (+ allarme) |

Anche la pompa comunica via Bluetooth con il sistema e, come il sensore, va controllata ogni 6 mesi.

### 3.Sistema

Il sistema si occupa di ricevere i dati dal sensore e comunicare alla pompa la quantità di insulina da iniettare, se necessario. 
Il sistema deve richiede i dati al sensore, ogni **10 minuti** (un clock interno al sistema manda un trigger che segnala la necessità di un controllo). Viene fatto un controllo così frequente perchè non è accettabile che il paziente abbia picchi glicemici troppo alti poichè essi possono portare a gravi conseguenze.

**Il sistema deve fornire informazioni al paziente e al medico**.
Il medico, sul display 1, deve poter vedere il valore glicemico nel sangue del paziente, la dose di insulina iniettata, la quantità di farmaco residua nella pompa e alterazioni repentine di glucosio nel sangue così da poter controllare la dieta del paziente.
Al paziente deve essere mostrato, sul display 2, la carica residua della batteria del sistema e la quantità di farmaco nella pompa.

Il sistema inoltre deve fornire **messaggi di allarme** per evitare situazioni di malfunzionamento. Cioè deve avvisare in particolare quando la carica residua della batteria del sistema è bassa (valore inferiore al 35%) e quando il farmaco nella pompa sta per finire ( valore inferiore a 60 UI). 
I valori sono tenuti alti rispetto ad altri sistemi perchè non deve accadere che il sistema non possa svolgere i suoi compiti per insufficienza del farmaco o batteria scarica

Inoltre il sistema deve sempre **iniettare il giusto quantitativo di farmaco** e la pompa non deve avere danni che provochino la perdita di insulina. Eventuali malfunzionamenti della pompa vengono subito comunicati al sistema che deve immediatamente allertare il paziente. 
In modo analogo anche il sensore non deve avere malfunzionamenti e la valutazione dei valori glicemici del sangue del paziente deve essere sempre precisa. Tale precisione è necessario per garantire la salute del paziente.

Per garantire questo il sistema deve implementare una **routine di controllo**, ogni minuto, per analizzare che i suoi componenti funzionino correttamente.
Sensore, pompa, display 1 e 2 e l'allarme devono avere un **numero di serie**,  e un **valore di controllo**. Essi, nel ciclo di routine devono comunicare tali dati al sistema.
Se li riceve corretti ed entro 15 secondo il funzionamento è considerato corretto. In caso contrario, anche se un solo componente presenta un malfunzionamento, il sistema deve comunicare immediatamente, con l'ausilio di un allarme, che non è più in grado di garantire il suo corretto funzionamento. Nel caso in cui siano la pompa o il sensore ad avere un malfunzionamento il sistema deve anche spegnersi in attessa di essere rispristinato dal medico o da un tecnico.
Il paziente, per sopperire a tale eventualità, deve avere a disposizione dosi monouso di insulina e un sensore di scorta.

| *Dispositivo* |*Numero di serie* |  *Valore di controllo* |
| --- | --- |  --- |
| *Sensore* | S-?????? | 500 |
| *Pompa* | P-?????? |  300 |
| *Display 1* | D1-????? | 100 |
| *Display 2* | D2-????? | 200 |
| *Allarme* | A-?????? |  33 |

? : indica un numero intero compreso tra 0 e 9. Ogni volta che un componente viene sostituito verrà aggiornato il sistema con il numero di serie corrispondente. Il numero di serie contiene 8 caratteri alfanumerici.
Inoltre il sensore e la pompa devono inviare al sistema il loro ultimo stato così da controntarlo con i dati attuali del sistema. 
Per il sensore **l'ultima misurazione dei valori glicemici** e per la pompa **la quantità residua di farmaco** e **l'ultima dose iniettate**.

## SCENARI

### Scenario 1: Iniezione di insulina
#### Assunzioni iniziali
Il sistema è in esecuzione. Il clock ha mandato il trigger al sistema il quale ha richiesto i dati al sensore. Il sensore ha analizzato i valori glicemici del sangue e ha inviato la risposta al sistema.
Il sistema ha analizzato i dati ricevuti dal sensore e ha calcolato la dose corretta di insulina da somministrare

#### Funzionamento normale
Il sistema manda il risultato dei calcoli appena svolti alla pompa che provvede immediatamente a iniettare la giusta dose di insulina. Nel caso in cui i valori glicemici del sangue siano nella norma e non sia necessaria la somministrazione, essa non verrà effettuata.
Il sistema calcola la quantità residua di farmaco e la confronta con quella che gli viene inviata dalla pompa così da aggiornare i suoi dati, tenere traccia degli avvenimenti ed essere pronto ad allertare il paziente e il medico in caso che il farmaco inizi a scarseggiare (meno di 60UI nella pompa).
Nel caso in cui il sistema valuti che non serva l' iniezione di insulina non procederà ad aggiornare la quantità di farmaco.

#### Eventuali problemi
Il valore di farmaco residuo calcolato dal sistema non coincide con quello inviatogli dalla pompa. Scatta allarme come nel caso di un malfunzionamento.

#### Stato del sistema alla conclusione
Il sistema torna ad attendere il prossimo trigger del clock.

### Scenario 2: Analisi dei valori glicemici
#### Assunzioni iniziali
Il sistema è in esecuzione. Il clock ha inviato il trigger al sistema il quale ha chiesto al sensore i dati relativi ai valori glicemici

#### Funzionamento normale
Il sistema riceve i dati dal sensore. Ora ha il compito di calcolare la dose di farmaco da iniettare sulla base dei valori glicemici del paziente. 
Se i valori di glucosio nel sangue sono alti imposta la giusta quantità. Con valore massimo ( > 500 mg/dL) imposterà la dose massima (16 UI), mentre se i valori sono nella norma lascierà a 0 UI la dose da somministrare. 
Nel mezzo la dose cala o aumenta in base al range in cui si trovano i valori glicemici nel paziente (come descritto nei requisiti).

#### Stato del sistema alla conclusione
Il sistema è pronto a inviare la giusta quantità di insulina da somministrare

### Scenario 3: Aggiornamento display
#### Assunzioni iniziali
Il sistema è in esecuzione. La pompa ha fatto l'iniezione oppure, essendoci i valori glicemici nella norma non ha svolto alcuna task

#### Funzionamento normale
Il sistema ora deve aggiornare i dati visualizzati nei due display. Nel display 2 aggiorna il valore della carica della batteria e la quantità residua di farmaco.
Nel display 1 aggiorna i due valori come nel display 2 e in più aggiorna altre informazioni: la quantità di farmaco somministrato, i valori di glucosio del paziente e la variazione dell'indice glicemico dall'ultima misurazione.

#### Stato del sistema alla conclusione
Il sistema riprende il suo funzionamento normale aspettando un altro trigger del clock

### Scenario 4: Allarme 
#### Assunzioni iniziali
Il sistema si trova in una situazione di allarme.
#### Funzionamento normale
Il sistema deve gestire la situazione di allarme, in base all'evento che la scatena.
Per valori di glicemia troppo alti ( > 500 mg/dL ) il sistema fa partire l'allarme così come se i valori sono troppo bassi ( < 70 mg/dL ). In questo caso verrà visualizzato anche un consiglio su quale cibo assumere (implementato come valore booleano. Il parametro *lowInsuline* se posto a *true* fornirà tale funzione).
In caso di batteria scarica o poco farmaco residuo nella pompa oltre ad un allarme visivo su entrambi i display viene attivato un allarme sonoro.

#### Stato del sistema alla conclusione
Il sistema è in carica o la pompa è stato rifornita di farmaco

### Scenario 5: Routine di controllo
#### Assunzioni iniziali
Il sistema è in esecuzione normalmente
#### Funzionamento normale
Arriva il trigger per il controllo di routine. Il sistema effettua una scanning di tutti i suoi componenti. Questo avviene chiedendo al sensore e alla pompa i dati relativi al loro corretto funzionanemnto (descritti nei requisiti), controlla che siano corretti e procede con la sua normale esecuzione.

#### Eventuali problemi
Un componente risponde in modo errato o non risponde (attesa di 15 secondi). In tal caso il sistema invia immediatamente un'allarme. Quest'ultimo saprà che dovrà contattare il medico poichè il sistema non è in grado di fornire correttamente il servizio. 
Nel caso in cui gli errori siano relativi alla pompa o al sensore il sistema si spegne in attesa di manutenzione.

#### Stato del sistema alla conclusione
Il sistema è in stato di allarme ma continua a fornire il proprio servizioo è spento per evitare dosaggi errati, in base al tipo di errore che ha scaturato l'allarme.

##TESTING

###Selezione dei test

Per la fase di unit testing sono stati selezionati tutti i metodi della classe *Controller* che gestisce i dati del sistema e prende le decisioni e alcuni metodi delle altre classi, le quali gesticono le operazioni che il sistema deve fare.
Queste ultime classi, come *Alarm*, *InsulinPomp*, *Display1* e *Display2*, sono state implementate per ricevere i dati e salvarli nei campi corretti ma, non avendo a disposizione l'hw, non svolgono nessuna funzionalità. La stessa cosa vale anche per la classe *Sensor*: è stato scelto di usare il metodo *java.lang.Math.random()* per non impostare un valore fisso di glucosio nel sangue ma avere valori diversi ad ogni controllo.

#### Primo unit test
Partendo dal primo test nella classe *UnitTest*, *testSystem()* implementa il test per il metodo *checkSystemStatus()* di *Controller* che si occupa di verificare che il sistema non abbia errori. Non ci sono valori di input da impostare, viene chiamata il medoto e si fa un'asserzione su una variabile: essa rimane a **true** se non ci sono errori nei componenti del sistema. Tale test controlla la funzionalità che implementa lo scenario 5, *routine di controllo*.

#### Secondo unit test
Il test *testPomp()* si occupa di controllare che la funzionalità dello scenario 1 sia corretta. 
Si vuole testare il metodo *getQuantity()* perciò viene creata una classe *InsulinPomp*, impostato il valore della dose a 8UI, valore medio tra i possibili valori di insulina e viene creata una lista, tipo di ritorno del metodo, a cui viene aggiunto il valore 342UI cioè l'insulina residua dopo l'iniezione. A questo punto viene chiamato il metodo e si fa l'asserzione: la lista creata deve essere uguale alla lista ritornata da *getQuantity()* e l'attributo *quantity* di *InsulinPomp* deve essere anch'esso uguale a 342UI.
Successivamente è stato testato il valore di insulina pari a 0 attraverso la chiamata al metodo *setDose()* di *InsulinPomp*. L'asserzione si assicura che il valore residuo di insulina nella pompa non cambi.
Infine viene chiamata più volte il metodo *setDose()* in modo da portare l'insulina residua nella pompa sotto di 60UI. Viene fatta un'asserzione in modo da controllare che il valore di ritorno sia una lista di grandezza 2: il primo valore è la quantità residua mentre il secondo è una costante (700) per indicare che l'insulina sta per terminare.

#### Terzo unit test
Il test successivo *testSensorController()* si occupa di controllare che i metodi *calculateDose()* e *sendDoseToPomp()* di *Controller* siano corretti. Viene impostato un valore per il campo *glucose* di *Controller* (scelto 250, valore medio tra i possibili valori di glucosio) poichè se esso viene richiesto al sensore non possiamo sapere quale sia il suo valore (*Sensor* usa metodo *Math.random()*). Viene chiamata il primo metodo per calcolare la dose di insulina e viene fatta un'asserzione sull'attributo *dose* di *Controller* per verificare che la dose calcolata sia corretta.
poi si chiama il secondo metodo *sendDoseToPomp()* in modo da inviare i dati alla pompa e calcolare la dose residua di insulina: questo viene fatto per evitare che la pompa abbia un malfunzionamento, sbagli a calcolare il valore residuo di insulina ed esaurisca il farmaco. Ancora una volta, tramite un'asserzione, viene controllato che tale calcolo sia corretto (risultato salvato nella variabile *currentInsQuantity*). Tale test rispecchia lo scenario 2 e lo scenario 1.

#### Quarto unit test
Il test *testDisplays()* controlla solamente che i parametri dei due display vengano impostati nel modo corretto. Viene chiamato il metodo *sendInfoToDisplays()* quando il sistema è nei suo valori di default e poi vengono fatte molte asserzioni per controllare che i valori assegnati alle variabili dei display corrispondano.

#### Quinto unit test
Il test *testSensorIsInRange()* controlla che il metodo *getGlucose()* di *Sensor* ritorni sempre valori compresi tra 0 e 600. Vengono fatti 10000 asserzioni all'interno di un ciclo

#### Altri test
I test visti fino ad ora controllare funzionamenti normali del sistema. Quelli successivi sono molto simili anche se si differenziano per il fatto che controllano situazioni in cui i valori di glucosio sono critici oppure il sistema è in condizioni di errore o di allarme. Per questo all'interno di questi test sono state fatte molte asserzioni per controllare che il parametro *ring* di *Alarm* sia a *true* nelle condizioni di allarme.
Degno di nota è il test *testSysError()* in cui viene controllato che, nel caso in cui ci sia un errore nella pompa (in questo caso il valore residuo ritornato dalla pompa non coincide con i calcoli del sistema), il sistema faccia partire l'allarme e poi si spenga (asserzione con stato di uscita uguale a 1).
Nei casi in cui il glucosio sia troppo basso o troppo alto o l'insulina nella pompa stia per usaurire viene controllato che l'allarme suoni (campo *ring* a *true*) e che vengano assegnati i giusti valori ai parametri dei displays.

#### Acceptance testing
Per l'acceptance testing sono stati creati due grandi test. Il primo prevede il funzionamento corretto del sistema in cui vengono impostati vari valori di glucosio nel sangue in modo da controllare tutto il possibile range di valori e di seguito sono state chiamate tutte i metodi visti prima negli unit test. Vengono fatte molte asserzioni per verificare che i valori del sistema siano corretti, ad esempio le dosi somministrate, la quantità di farmaco residua e che i parametri nei displays siano corretti (anche nei casi in cui il valore del glucosio sia troppo alto o troppo basso). 
Il secondo test controlla il sistema nel suo complesso, chiamando sempre gli stessi metodi, e porta il sistema in condizioni di allarme: la quantità residua di insulina è al di sotto della soglia di sicurezza quindi viene impostato a *true* il campo *ring* di *Alarm* (controllato da un'asserzione) e infine viene ritornato un valore errato  per *dose* dalla pompa. Quindi non potendo permettere che la pompa somministri quantità sbagliate di farmaco il test controlla che il sistema venga spento.

### Copertura dei test
Avviando gli unit test e controllando la copertura del codice si può vedere che esso è coperto per l'82%. Non viene raggiunto il 100% poichè la classe *Clock* non è stata testata in quanto conta i secondi attraverso una funzione di sistema e anche la classe *InsulinSystem* non è coperta dai test poichè prevede solamente un ciclo while infinito che chiama i metodi per il check del paziente ogni 10 secondi e il metodo per il controllo del sistema ogni 3.
Anche la classe *Controller* con una copertura dell'87% contribuisce ad abbassare quella totale. Le parti "scoperte" sono la funzione di sistema per spegnere il programma, il metodo *doseFromSensor()* che si limita a chiamare il metodo già testato *getGlucose()* della classe *Sensor*. In più non vengono coperti tutti i branches del metodo *calculateDose()* dal momento che differiscono solo per il valore della dose. Sono stati testati solo i valori al limite che provocano il caso di allarme, il valore che porta a non dover somministrare una dose e un altro valore intermedio.

## Note aggiuntive sulle decisioni implementative
I controlli per monitorare il sistema e per monitorare il paziente vengono fatti, rispettivamente, dopo 3 e dopo 30 secondi per poter osservare il funzionamento del sistema senza attendere troppo tempo.
I displays aggiornano solamente i loro parametri così come l'allarme. Nel caso in cui fosse disponibile l'harware si potrebbe implementare la stampa e il segnale acustico.
In modo analogo lavora la pompa che per mancanza del componente fisico non potrà compiere alcuna iniezione.
Inoltre, il numero di serie e il valore di controllo non porteranno mai ad un errore in questa demo poichè essi sono valori statici. Si assume che eventuali errori occorranno in seguito al malfunzionamento della connessione bluetooth o quando un componente viene sostituito e, di conseguenza, cambieranno i suo valori di controllo (ancora una volta, non avendo l'hardware, questa parte è lasciata ad un'implementazione futura). Infatti il caso di test che porta ad un mal funzionamento è stato implementato fornendo un valore errato nella misurazione del valore residuo di insulina da parte della pompa.

