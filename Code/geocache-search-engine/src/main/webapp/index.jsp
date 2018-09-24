<html>
<h2><strong>Geocache Search Engine REST Description <br /></strong></h2>
<p>run your requests on: localhost:8080/geocache-search-engine/webapi/...</p>
<ul>
	<li>.../search/{query}
		<ul>
			<li>Search method for standard user search. Query is the search term entered by the user.</li>
		</ul>
	</li>
</ul>
<ul>
	<li>.../search/extended/{query}
		<ul>
			<li>Search method for extended user search. Query is the search term and parameters entered by the user.</li>
		</ul>
	</li>
</ul>
<ul>
	<li>.../suggest/{query}
		<ul>
			<li>Suggestion method for receiving up to five suggestions. Query is the suggestion term entered by the user.</li>
		</ul>
	</li>
</ul>
<ul>
	<li>.../index/create
		<ul>
			<li>Creation method for the index.<strong>ATTENTION:</strong> Could take some time to recreate the index!</li>
		</ul>
	</li>
</ul>
<ul>
	<li>.../logs/{query}
		<ul>
			<li>Log method for query logging.</li>
		</ul>
	</li>
</ul>
<p>&nbsp;</p>
</html>