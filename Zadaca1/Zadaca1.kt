interface Osoba{
    fun getId(): String
    fun getTitle(): String
}

open class Programer(var ime: String, var prezime: String,var titula: String, var godineIskustva: Int, var zemlja: String, var jezici: List<String> ): Osoba{
       
    val jeziciNorm = jezici.map { it.lowercase() }
   
    override fun getId(): String {
        return ime + "_" + prezime
}
    override fun getTitle(): String{
        return titula
    }
           
    //Odradjeno uz pomoc AI alata posto nisam vidjela slican primjer na laboratorijskim vjezbama, drugi predlozeni nacin je bio preko 'require'
    // u jednoj liniji ali mi se ovaj cinio laksi za pocetak
    init {
        require(ime.isNotBlank()) { "Ime ne smije biti prazno" }
        require(prezime.isNotBlank()) { "Prezime ne smije biti prazno" }
        require(godineIskustva >= 0) { "Godine iskustva moraju biti pozitivne" }
        require(jezici.isNotEmpty()) { "Lista jezika ne smije biti prazna" }
    }

}
class BackendDeveloper(ime: String, prezime: String, titula: String, godineIskustva: Int, zemlja: String, jezici: List<String>, var backendFramework: String
): Programer(ime, prezime, titula, godineIskustva, zemlja, jezici) {

}

class FrontendDeveloper(ime: String, prezime: String, titula: String, godineIskustva: Int, zemlja: String, jezici: List<String>, var frontendFramework: String
): Programer(ime, prezime, titula, godineIskustva, zemlja, jezici) {

}

// Prvi nacin, odradjen uz pomoc AI alata zbog manjka iskustva s navedenim metodama
fun brojProgrameraPoJeziku1(programeri: List<Programer>): Map<String, Int> {
    return programeri.flatMap { it.jeziciNorm }
        .groupingBy { it }
        .eachCount()
}

// Drugi nacin, rucno brojanje - metod getOrDefault je bio optimizacija predlozena od AI alata kako bi kod bio kraci, jasniji i samim tim jednostavniji
//getOrDefault vraca vrijednost za proslijedjeni kljuc iz mape ako postoji, a ako ne postoji vraca specificiranu default vrijednost
fun brojProgrameraPoJeziku2(programeri: List<Programer>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    for (p in programeri) {
        for (j in p.jeziciNorm) {
            result[j] = result.getOrDefault(j, 0) + 1
        }
    }
    return result
}

// prvi nacin - Opet upotrijebljena pomoc AI alata
fun prosjecnoIskustvo1(programeri: List<Programer>): Map<String, Double> {
    return programeri
        .flatMap { p -> p.jeziciNorm.map { it to p.godineIskustva } }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, lista) -> lista.sum().toDouble() / lista.size }
}

// drugi nacin sa getOrDefault
fun prosjecnoIskustvo2(programeri: List<Programer>): Map<String, Double> {
    val suma = mutableMapOf<String, Int>()
    val broj = mutableMapOf<String, Int>()

    for (p in programeri) {
        for (j in p.jeziciNorm) {
            suma[j] = suma.getOrDefault(j, 0) + p.godineIskustva
            broj[j] = broj.getOrDefault(j, 0) + 1
        }
    }

    val rezultat = mutableMapOf<String, Double>()
    for (j in suma.keys) {
        rezultat[j] = suma[j]!!.toDouble() / broj[j]!!  //linija optimizirana pomocu AI s obzirom da sam se saplitala oko bacanja iznimki
// !! je operator koji forsira non-null vrijednost (koristi se u slucajevima kada smo sigurni da postoji i baca iznimku ako se ipak nadje null vrijednost)
    }

    return rezultat
}

fun filtrirajFramework(programeri: List<Programer>, framework: String): List<Programer> {
    val rezultat = mutableListOf<Programer>()
    for (p in programeri) {
        if (p is BackendDeveloper) { //p is BackendDeveloper je optimizacija predlozena od strane AI, tj da je to specificna sintaksa koja provjerava da li je objekat instanca specificne klase
            if (p.backendFramework.equals(framework, ignoreCase = true)) {
                rezultat.add(p)
            }
        } else if (p is FrontendDeveloper) {
            if (p.frontendFramework.equals(framework, ignoreCase = true)) {
                rezultat.add(p)
            }
        }
    }
    return rezultat
}

fun ispisiProgramere(programeri: List<Programer>) {
    for (p in programeri) {
        val tip = when (p) {
            is BackendDeveloper -> "Backend developer"
            is FrontendDeveloper -> "Frontend developer"
            else -> "Programer"
        }

        val framework = when (p) {
            is BackendDeveloper -> p.backendFramework
            is FrontendDeveloper -> p.frontendFramework
            else -> "-"
        }

        println("${p.getId()} — $tip — jezici: ${p.jeziciNorm.joinToString(", ")} — framework: $framework")
    }
}


fun main() {
    //main je stvoren uz pomoc AI alata jer je samo testiranje kreiranih klasa i metoda
        val programeri = listOf(
        BackendDeveloper("Amila", "H", "Dr", 5, "BA", listOf("Kotlin", "Java"), "Spring Boot"),
        FrontendDeveloper("Lejla", "S", "Mr", 4, "DE", listOf("JavaScript", "TypeScript"), "React"),
        BackendDeveloper("Tarik", "B", "Mr", 3, "BA", listOf("Java", "Kotlin"), "Ktor"),
        FrontendDeveloper("Sara", "M", "Ms", 2, "BA", listOf("JavaScript", "HTML", "CSS"), "Vue.js"),
        BackendDeveloper("Dino", "K", "Mr", 6, "DE", listOf("Java", "Python"), "Spring Boot")
    )

    // 1) Prikaz svih programera
    println("=== Svi programeri ===")
    ispisiProgramere(programeri)

    // 2) Prebrojavanje jezika
    println("\n=== Broj programera po jeziku (groupBy) ===")
    println(brojProgrameraPoJeziku1(programeri))
    println("\n=== Broj programera po jeziku (manualno) ===")
    println(brojProgrameraPoJeziku2(programeri))

    // 3) Prosječno iskustvo po jeziku
    println("\n=== Prosječno iskustvo po jeziku (groupBy) ===")
    println(prosjecnoIskustvo1(programeri))
    println("\n=== Prosječno iskustvo po jeziku (manualno) ===")
    println(prosjecnoIskustvo2(programeri))

    // 4) Filtriranje po frameworku
    val filtrirani = filtrirajFramework(programeri, "Spring Boot")
    println("\n=== Programeri koji koriste Spring Boot ===")
    ispisiProgramere(filtrirani)
}

