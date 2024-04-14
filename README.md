# OOPP Splitty

This repository contains team 37's take on the Splitty assignment
### Execution of the project:
1. If you run the server first:
- you can run the client, connect to the url of the server and operate freely
2. You can also run the client first
- In this case you will only be able to see the starting screen and whatever you click will ask you to run the server/enter the proper url

### Access to features:
1. Admin:
- you can access the admin overview by entering the correct server url and clicking the admin button (bottom right) on the start page
- you can generate a password that will be visible in the server terminal
- from there -> every event has three buttons
- the first button will download a JSON copy of the event in the `json` folder in `resources`
- the second button will copy a JSON copy of the event on the clipboard
- the third button is for deletion of the event (there is a confirmation dialog)
- The refresh button will refresh the events
- In the restore menu you can restore events if you have JSON copy of an event at hand
- And the order by button will order the events by the selected criterion

2. Client:
- When the client connects to the correct server url, they are given an overview of the events
- Once you have entered as a client you don't have access to admin
- You can change the language to your desired one from the menu in the top right
- You can enter an event only if you have the invite code
- You can create events by clicking on 'Add Event' button and enter the required information
- When you enter the invite code in the text field on the top and click the button (or click enter on the keyboard) you are redirected to the event
- In the event details you have access to participants, expenses, statistics
- You can see/edit every participant by clicking on them
- You can see every expense in the box on the right. And they can also be filtered and ordered.
- There are buttons for both adding and expense and a participants
- There is a change event name text field
- When you click on the 'Send invite' button you are redirected to a page for sending emails
- There you can enter all the emails you want to send the invite code of the event to
- In an event you can create an expense. While creating an expense you can select tags.
- The way tags work is you can create a custom one which works by filling in the field and selecting a color and hitting OK. It will show up on the page.
- By then again clicking on the tag you can edit it by changing the text or color and hitting the edit button or you can delete it by hitting delete.
- After you have added or edited the tags to your desire you can freely close the page and the tags will show up in the expense creation menu.
- You can create an expense by filling in the required fields and hitting the create button
- From the event you can access the statistics by hitting the statistics button

3. Long polling
- long-polling is implemented for the events of the admin
- when an event is created the admin can immediately see the new events
- The implementation can be found in `AdminOverviewPageCtrl`, `ServerUtils` and `EventRestController`
- In `AdminOverviewPageCtrl` the connection is established and the client is 'registered' for updates
- In the `ServerUtils` the requests are sent
- In the `EventRestController`, `createEvent()` updates all the listener's information

4. WebSockets
- web socket has been implemented for the expenses of the events
- when a new expense is created the change is immediately visible (unless the connection has timed-out)
- The implementation of it can be seen in `ExpenseRestController` and `ServerUtils`

5. Controller
- there is a InviteController in the serve/api folder
- if you access localhost:8080/invites (the server and port could be different) through the browser you can access all the available invite codes
- we used thymeleaf as our template engine

### HCI
- there is an `ESC` shortcut for all scenes, that lets you go back to the previou page
- All "forms" have `Enter` shortcut for "submitting" it
- You can navigate through the whole application with `ESC`, `Enter`, `Tab` buttons and also the arrows
- Buttons in admin have icons in addition to text
- When you hover on the event buttons in admin, text is also displayed
- There are error messages for every invalid input
- There are pop-ups for creation, deletion etc.
- There are labels when an invite code is copied, an event is restored, email is sent (the email is invalid), etc.

### Adding a new language
1. After selecting the _Add language_ option a file gets created in the **language resources folder**.
2. The file is called `lang_language_country.properties`.
3. Open that file.
4. Each word ends with a ' = ', after each ' = ' write your desired the translation
5. Make sure there is something is written after the ' = '
6. Change the file name into this format: `lang_[abbreviation of the language]_[abbreviation of the country].properties`

(Optional) If desired, a flag can be added. Drag and move the image to the **flags resource folder**.
Make sure it is a png image and change the file name into this format: `flag_[abbreviation of the language]_[abbreviation of the country].png`

**language resources folder**: client/src/main/resources/languages
**flag resources folder**: client/src/main/resources/images/flags

