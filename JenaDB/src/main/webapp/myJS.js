function doQuery()
{
//alert('doQuery...');	
	/*if((document.getElementById('tf01').value!='')&&(document.getElementById('tf02').value!='')&&(document.getElementById('tf03').value!=''))
	{*/
		var q_str = 'reqType=doQuery';

		q_str = q_str+'&param01='+document.getElementById('bookerName').value;
		q_str = q_str+'&param02='+document.getElementById('nGuests').value;
		q_str = q_str+'&param03='+document.getElementById('nBedrooms').value;
		q_str = q_str+'&param04='+document.getElementById('distanceToLake').value;
		q_str = q_str+'&param05='+document.getElementById('closestCity').value;
		q_str = q_str+'&param06='+document.getElementById('maxDistanceToCity').value;
		q_str = q_str+'&param07='+document.getElementById('nDays').value;
		q_str = q_str+'&param08='+document.getElementById('startDate').value;
		q_str = q_str+'&param09='+document.getElementById('nDaysShift').value;

		doAjax('Booking',q_str,'doQuery_back','post',0);
	/*}else
	{
		alert('Please, fill all the search fields...');
	}*/
}

function doQuery_back(result)
{
alert(result);
}





