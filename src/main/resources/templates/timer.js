
function timer()
{
if( --timeout > 0 )
{
document.getElementById("clock").innerHTML=timeout;
window.setTimeout( "timer()", 1000 );
}
else
{
document.getElementById("clock").innerHTML = "Time over";
document.submit();
///disable submit-button etc
}
}



