package com.example.insuranceapp.listener


/* This interface must be implemented by activities that contain this
        * fragment to allow an interaction in this fragment to be communicated
        * to the activity and potentially other fragments contained in that
        * activity.
        * <p/>
        * See the Android Training lesson <a href=
        * "http://developer.android.com/training/basics/fragments/communicating.html"
        * >Communicating with Other Fragments</a> for more information.
        */
interface OnFragmentListItemSelectListener {
    // TODO: Update argument type and name
    fun onListItemSelected(itemId: Int, data: Any)

    fun onListItemLongClicked(itemId: Int, data: Any)
    fun onListItemLongClickedSnd(itemId: Int, data: Any, position: Int)
}
