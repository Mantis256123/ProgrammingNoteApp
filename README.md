# Užrašų sistemos Java projektas

## 📌 Paskirtis
„Note App“ yra objektinio programavimo principus demonstruojanti užrašų valdymo sistema, skirta:
- Kurti, peržiūrėti, redaguoti, trinti ir išsaugoti įvairių tipų užrašus
- Sukurti ne tik tekstinius, bet ir:
  - Programavimo užrašus
  - Testavimo užrašus

## 🚀 Paleidimas
**Reikalavimai:**
- JDK 17 arba naujesnė versija

**Paleidimo komanda:**
```bash
java AppGui.NoteAppGUI
```
## Funkcionalumas
Programoje galima atlikti šiuos veiksmus:
-	Sukurti naują užrašą:
-	Paprastą užrašą (Note)
-	Programavimo užrašą (ProgrammingNote)
-	Testavimo užrašą (TestingNote)
-	Peržiūrėti visus įrašytus užrašus
-	Redaguoti egzistuojančius užrašus
-	Ištrinti nereikalingus užrašus
-	Išsaugoti ir įkelti užrašus naudojant objektų serializaciją
-	Naudoti versijų kontrolę – saugoti ir atkurti skirtingas užrašo versijas
-	Atidėti užrašo įvykdymo terminą
  
## 📦 Pagrindinės klasės

| Klasė                | Paskirtis                                                                 |
|----------------------|---------------------------------------------------------------------------|
| `Note`               | Bazinė klasė, apibrėžianti bendrus laukus ir funkcijas visiems užrašams   |
| `ProgrammingNote`    | Paveldi `Note`, papildo ją programavimo kalba ir kodo fragmentu           |
| `TestingNote`        | Paveldi `Note`, prideda testavimo atvejį, jo būseną ir būtinybės žymę     |
| `NoteAppGUI`         | Grafinės vartotojo sąsajos valdymo klasė                                  |
| `TitleLengthException` | Išimtis, išmetama, kai pavadinimas viršija leistiną ilgį              |

---

## 🧩 Naudoti projektavimo šablonai (Design Patterns)

### 📄 Template Method Pattern

Klasė `Note` veikia kaip bazinis šablonas su bendra funkcionalumu, o specializuoti užrašų tipai, tokie kaip `ProgrammingNote` ir `TestingNote` (naudojami grafinėje sąsajoje), jį išplečia pridėdami specifinį elgesį.

### 🧠 Strategy Pattern

Skirtingi užrašų tipai (`Note`, `ProgrammingNote`, `TestingNote`) atitinka skirtingas strategijas, kaip tvarkyti užrašams būdingą funkcionalumą, kaip matyti grafinės sąsajos užrašų tipo pasirinkime.
