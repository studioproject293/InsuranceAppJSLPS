
import com.orm.SugarRecord

class Table7Db : SugarRecord {
    var clustercode: String? = null
        private set
    var villagecode: String? = null
        private set
    var pgcode: String? = null
        private set
    var pgname: String? = null
        private set


    constructor() : super()
    constructor(
        clustercode: String?,
        villagecode: String?,
        pgcode: String?,
        pgname: String?
    ) : super() {
        this.clustercode = clustercode
        this.villagecode = villagecode
        this.pgcode = pgcode
        this.pgname = pgname
    }

    override fun toString(): String {
        return pgname.toString() // What to display in the Spinner list.
    }
}