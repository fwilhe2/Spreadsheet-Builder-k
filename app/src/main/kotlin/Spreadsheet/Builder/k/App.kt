package Spreadsheet.Builder.k

import builders.dsl.spreadsheet.builder.data.DataSpreadsheetBuilder
import builders.dsl.spreadsheet.builder.poi.PoiSpreadsheetBuilder

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
    val file = java.io.File("spreadsheet.xlsx")
    val s = DataSpreadsheetBuilder.create()

    s.build {
        it.sheet("Sample") {
            repeat(10) { n ->
                it.row {
                    repeat(10) { m ->
                        it.cell{
                            //IF(MOD(13,5)=0, "foo", "bar")
                            //IFS(MOD($current, 15)=0, \"foobar\", MOD($current, 5)=0, \"foo\", MOD($current, 3)=0, \"bar\")
                            val current = (n + 1) * (m + 1)
                            it.formula("IFS(MOD($current, 15)=0, \"foobar\", MOD($current, 5)=0, \"foo\", MOD($current, 3)=0, \"bar\", 1=1, $current)")
                        }
                    }
                }
            }
        }
    }

    val m = s.getData()
    println(m)

    PoiSpreadsheetBuilder.create(file).build { w ->
        w.sheet("Sample") {
            repeat(10) { n ->
                it.row {
                    repeat(10) { m ->
                        it.cell {
                            val current = (n + 1) * (m + 1)
                            it.formula("IFS(MOD($current, 15)=0, \"foobar\", MOD($current, 5)=0, \"foo\", MOD($current, 3)=0, \"bar\", 1=1, $current)")
                        }
                    }
                }
            }
        }
    }
}
