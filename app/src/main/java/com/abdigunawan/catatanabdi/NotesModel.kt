package com.abdigunawan.catatanabdi

import java.io.Serializable

data class NotesModel ( val notes: List<Data> ){
    data class Data (val id: String?, val judul: String?, val catatan: String?) : Serializable
}