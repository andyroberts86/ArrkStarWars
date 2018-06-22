# ArrkStarWars

A couple of notes about what I've omitted and why:

1: UI & End to End tests. I'd normally write these in Espresso using RESTMock and use them to validate the flow through the app, and to check that the various fields are updated on the screen correctly. However that felt out of scope for this test.

2: There was SWAPI library available for Android, but it has no license and no tests, so I avoided using it.

3: Data Validation. I've avoided doing data validation on the fields as at this stage I'd rather the app crash when it encounters corrupted data, so we can more quickly identify the issue.
