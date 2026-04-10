package com.example.starstudent.core.view.uiComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.starstudent.core.domain.navigation.NavigationOptions
import com.example.starstudent.planner.data.entities.Tasks
import okhttp3.internal.toImmutableList
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale
import java.util.Date

/* UI component for backgrounds.
*/
@Composable
fun Background() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush
                .verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.secondary)))
            .testTag("background")
    )
}

@Composable
fun mediumIconWidget(
    repIcon: ImageVector,
    quanIcon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier)
    {
        Column(
            modifier = modifier
                .aspectRatio(1f)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(36.dp)
                )
                .padding(start = 20.dp,end = 20.dp, top = 36.dp, bottom = 10.dp)
                .clickable{onClick()}
                .testTag("mediumIconWidgetBackground"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center
            )
            {
                Icon(
                    imageVector = repIcon,
                    contentDescription = "Representation Icon",
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("mediumIconWidgetRepIcon"),
                    tint = MaterialTheme.colorScheme.tertiary

                )

                Spacer(modifier = Modifier.width(5.dp))

                Icon(
                    imageVector = quanIcon,
                    contentDescription = "Quantify Icon",
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("mediumIconWidgetQuanIcon"),
                    tint = MaterialTheme.colorScheme.background

                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = label,
                    modifier = Modifier
                        .size(100.dp)
                        .testTag("mediumIconWidgetText"),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
            )
        }
}

@Composable
fun mediumIconWidget(
    repIcon: ImageVector,
    quanIcon: ImageVector,
    label: String,
    modifier: Modifier)
{
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(36.dp)
            )
            .padding(start = 20.dp,end = 20.dp, top = 36.dp, bottom = 10.dp)
            .testTag("mediumIconWidgetBackground"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        )
        {
            Icon(
                imageVector = repIcon,
                contentDescription = "Representation Icon",
                modifier = Modifier
                    .size(36.dp)
                    .testTag("mediumIconWidgetRepIcon"),
                tint = MaterialTheme.colorScheme.tertiary

            )

            Spacer(modifier = Modifier.width(5.dp))

            Icon(
                imageVector = quanIcon,
                contentDescription = "Quantify Icon",
                modifier = Modifier
                    .size(36.dp)
                    .testTag("mediumIconWidgetQuanIcon"),
                tint = MaterialTheme.colorScheme.background

            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = label,
            modifier = Modifier
                .size(100.dp)
                .testTag("mediumIconWidgetText"),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}


/* UI component for a small progress widget.
*/
@Composable
fun smallProgressWidget(
    numCompleteTasks: Int,
    numTasks: Int,
    label: String,
    textSize:Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier){

    var safeTasks = 1

    safeTasks = if (numTasks == 0){
        1
    }else{
        numTasks
    }


    Box(
        modifier = modifier
            .aspectRatio(1f)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .testTag("smallProgressWidgetBackground")
            .clickable(onClick = onClick),
    ){
        CircularProgressIndicator(
            progress = numCompleteTasks / safeTasks.toFloat(),
            modifier = Modifier.size(80.dp)
                .align(Alignment.Center)
                .testTag("smallProgressWidgetProgressBar"),
            color = MaterialTheme.colorScheme.tertiary,
            trackColor = MaterialTheme.colorScheme.background,
            strokeWidth = 10.dp,
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center
            )
            .padding(top = 6.dp)) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.background,
                fontSize = textSize.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .testTag("smallProgressWidgetNameLabel")
            )

            Text(
                text = "$numCompleteTasks/$numTasks",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 10.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .testTag("smallProgressWidgetTaskLabel")
            )

        }

    }

}


/* UI component for the large navigation widget.
*/
@Composable
fun largeNavWidget(
    repIcon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    navOnClick: () -> Unit){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .height(100.dp)
            .padding(20.dp)
            .clickable{navOnClick()}
            .testTag("largeNavWidgetBackground"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector =repIcon,
            contentDescription = "Representation Icon",
            modifier = Modifier
                .size(44.dp)
                .testTag("largeNavWidgetRepIcon"),
            tint = MaterialTheme.colorScheme.background

        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.background,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier
                    .testTag("largeNavWidgetTitleLabel")
            )

            Text(
                text = description,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetDescriptionLabel")
            )
        }
    }
}

/* UI component for the large navigation widget.
*/
@Composable
fun varLargeNavWidget(
    repIcon: ImageVector,
    title: String,
    titleSize: Int,
    description: String,
    modifier: Modifier = Modifier,
    navOnClick: () -> Unit){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(24.dp)
            )
            .height(100.dp)
            .padding(20.dp)
            .clickable{navOnClick()}
            .testTag("largeNavWidgetBackground"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector =repIcon,
            contentDescription = "Representation Icon",
            modifier = Modifier
                .size(44.dp)
                .testTag("largeNavWidgetRepIcon"),
            tint = MaterialTheme.colorScheme.background

        )

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.background,
                fontSize = titleSize.sp,
                fontWeight = FontWeight.Bold ,
                modifier = Modifier
                    .testTag("largeNavWidgetTitleLabel")
            )

            Text(
                text = description,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .testTag("largeNavWidgetDescriptionLabel")
            )
        }
    }
}


/* UI component for avatar window.
*/
@Composable
fun avatarWindow(
    prompt: String,
    modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.secondary)),
                shape = RoundedCornerShape(24.dp
                ))
            .height(300.dp)
            .width(160.dp)
            .padding(5.dp, 2.dp)
            .testTag("avatarWindowBackground")

    ) {
        Text(
            text = prompt,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag("avatarWindowLabel")
            )
    }
}

/* UI component for avatar window.
*/
@Composable
fun smallAvatarWindow(
    prompt: String,
    modifier: Modifier = Modifier,
    status: String){
    Box(
        modifier = modifier
            .background(Brush.verticalGradient(listOf(MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.secondary)),
                shape = RoundedCornerShape(24.dp
                ))
            .height(150.dp)
            .fillMaxWidth()
            .padding(5.dp, 2.dp)
            .testTag("avatarWindowBackground")

    ) {
        Text(
            text = status,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .testTag("avatarWindowLabel")
        )

        Text(
            text = prompt,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .testTag("avatarWindowLabel")
        )
    }
}


/* UI component for text field.
*/
@Composable
fun textField(header: String, info: String, height: Int){
    Column{
        Text(
            text = header,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .testTag("textFieldHeaderLabel")
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(36f),
                    color = MaterialTheme.colorScheme.background
                )
                .fillMaxWidth()
                .height(height.dp)
                .padding(4.dp)
                .testTag("textFieldBackground")
        ){

            Text(
                text = info,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .testTag("textFieldInfoLabel")
                    .padding(10.dp)
            )
        }
    }
}

/* UI component for spacers.
*/
@Composable
fun spacer(modifier :Modifier){
    Spacer(modifier
        .height(20.dp))
}



/* UI component for input fields.
*/
@Composable
fun inputField(label: String,
               value: String,
               seqNumber: Int?,
               onValueChange: (String) -> Unit){

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(text = label, fontSize = 12.sp)
                },
        singleLine = true,
        shape = RoundedCornerShape(36f),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Left
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("inputField$seqNumber")
    )
}

@Composable
fun numInputField(
                label: String,
                value: String,
                seqNumber: Int?,
                keyboardType: KeyboardType,
                onValueChange: (String) -> Unit){

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {Text(text = label, fontSize = 12.sp)
        },
        singleLine = true,
        shape = RoundedCornerShape(36f),
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Left
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("numInputField$seqNumber")
    )
}


/* UI component for buttons.
*/
@Composable
fun button(label : String, seqNumber: Int?, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .testTag("button$seqNumber")
    ){

        Text(text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .testTag("buttonLabel$seqNumber")
        )

    }
}

//UI for the top banner
@Composable
fun TopBanner(
    username : String,
    date: String,
    list: List<NavigationOptions>,
    showNav: Boolean,
    onDismissNav: () -> Unit,
    navOnClick: () -> Unit,
    profileOnClick: () -> Unit
    ){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)
            .testTag("topBanner"),

        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {navOnClick()}) {
                Icon(
                    modifier = Modifier
                        .testTag("topBannerMenuIcon"),
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.onPrimary)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column{
                Text(
                    modifier = Modifier
                        .testTag("topBannerTextUsername"),
                    text = ("Hello, $username"),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .testTag("topBannerTextDate"),
                    text = date,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 12.sp
                )
            }
        }

        IconButton(onClick = {profileOnClick()})
        {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Picture",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .testTag("topBannerProfilePicture")
            )
        }

        navigationDropDown(
            list,
            showNav,
            onDismissNav
        )
    }
}

//Formatting the banner to the top of the screen
@Composable
fun BannerFormat(
    username : String,
    date: String,
    profileOnClick : () -> Unit,
    list: List<NavigationOptions>,
    showNav: Boolean,
    navOnClick: () -> Unit,
    onDismissNav: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        topBar = {
            TopBanner(
                username,
                date,
                list,
                showNav,
                onDismissNav,
                navOnClick,
                profileOnClick
            )
        }
    ) {
       padding -> content(padding)
    }
}

//Toggle UI - not scalable
@Composable
fun toggle(
    header: String,
    isChecked: Boolean,
    onChange: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("toggle")
    ) {

        Text(
            text = header,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .testTag("textFieldHeaderLabel")
        )

        Spacer(modifier = Modifier.height(6.dp))

        Switch(
            checked = isChecked,
            onCheckedChange = {onChange()},
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.background,
                checkedTrackColor = MaterialTheme.colorScheme.secondary,
                uncheckedThumbColor = MaterialTheme.colorScheme.background,
                uncheckedTrackColor = MaterialTheme.colorScheme.primary

            )
        )
    }
}

// Updated toggle - scalable
@Composable
fun simpleToggle(
    scale : Float,
    isChecked : Boolean,
    onChange: () -> Unit
){
    Switch(
        checked = isChecked,
        onCheckedChange = {onChange()},
        colors = SwitchDefaults.colors(
            checkedThumbColor = MaterialTheme.colorScheme.background,
            checkedTrackColor = MaterialTheme.colorScheme.secondary,
            uncheckedThumbColor = MaterialTheme.colorScheme.background,
            uncheckedTrackColor = MaterialTheme.colorScheme.primary

        ),
        modifier = Modifier.scale(scale)
    )
}

// Dropdown for navigation options
@Composable
fun navigationDropDown(
    list: List<NavigationOptions>,
    expanded : Boolean,
    onDismiss: () -> Unit
){
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {onDismiss()},
        modifier = Modifier
            .heightIn(max= 340.dp)
            .width(180.dp)
            .background(MaterialTheme.colorScheme.primary)
    ){
        list.forEach{ option ->
            DropdownMenuItem(
                text = {
                    Text(
                        option.label,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                    )
                },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                ,
                onClick = {option.navigation()}
            )
        }
    }
}

//Data class for different calendar cells on the custom calendar grid
sealed class CalendarItem {
    data class Weekday(val day: Int) : CalendarItem()
    object Empty : CalendarItem()
    data class Day(val date: Date, val signal: Boolean) : CalendarItem()
}

//Get the days of the week as a list of Ints
fun getWeekDays(): List<Int> {
    val lista = (1..7).toList()
    return ((lista.drop(1) + lista.take(1)).toImmutableList())
}

//Return the string of a day from an Int
private fun Int.getDayOfWeek3Letters(): String? = Calendar.getInstance().apply {
    set(Calendar.DAY_OF_WEEK, this@getDayOfWeek3Letters)
}.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())

//Format year into a string
private fun Date.formatToYear(): String = SimpleDateFormat("yyyy", Locale.getDefault()).format(this)
//Format month into a string
private fun Date.formatToMonthString(): String = SimpleDateFormat("MMMM", Locale.getDefault()).format(this)
//Format date into a string
private fun Date.formatToCalendarDate() : String = SimpleDateFormat("d", Locale.getDefault()).format(this)

// Day cell on the custom calendar grid
@Composable
private fun WeekdayCell(
    weekday: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp),
        contentAlignment = Alignment.Center
    ) {
        weekday.getDayOfWeek3Letters()?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


//Get the day of the month from a date
private fun Date.formatToWeekDay(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_WEEK)
}

//The number of spaces the first day of the month is away from Monday
private fun getOffset(firstDay : Int) : Int{
    return (firstDay + 5) % 7
}


//Returns a string of the week as a string
private fun weekdayLabelsList(
    dates: List<Date>
): List<CalendarItem> {

    val items = mutableListOf<CalendarItem>()

    val weekdays = getWeekDays()

    // Header
    weekdays.forEach {
        items.add(CalendarItem.Weekday(it))
    }

    val firstDay = dates.first().formatToWeekDay()

    val offset = getOffset(firstDay)

    // Empty cells
    repeat(offset.coerceAtLeast(0)) {
        items.add(CalendarItem.Empty)
    }

    // Actual days
    dates.forEach {
        items.add(CalendarItem.Day(it, false))
    }

    return items
}


//Returns a custom calendar grid
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    dates: List<Date>,
    tasks: List<Tasks>,
    onClick: (Date) -> Unit,
    prevButtonClick: () -> Unit,
    nextButtonClick: () -> Unit,
    monthButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(){

            IconButton(onClick = {prevButtonClick()}) {
                Icon(
                    modifier = Modifier
                        .testTag("calendarPrevButton"),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.secondary)
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(onClick = {monthButtonClick()})
            ) {
                Text(
                    text = dates.first().formatToMonthString(),
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = dates.first().formatToYear(),
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            IconButton(onClick = {nextButtonClick()}) {
                Icon(
                    modifier = Modifier
                        .testTag("calendarPrevButton"),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.secondary)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        val taskDays = remember(tasks){
            tasks.map {it.dueDate}.map {
                millis ->
                Instant.ofEpochMilli(millis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            }.toSet()
        }

        val items = remember(dates,taskDays) {

            val today = LocalDate.now()

            weekdayLabelsList(dates).map{ item ->
                when (item){
                   is CalendarItem.Day ->  {
                       val localDate = item.date.toInstant()
                           .atZone(ZoneId.systemDefault())
                           .toLocalDate()
                       item.copy(
                           signal = (taskDays.contains(localDate) || (today == localDate))
                       )
                   }
                    else -> item
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            userScrollEnabled = false,
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {

            items(items.size) { index ->
                when (val item = items[index]) {

                    is CalendarItem.Weekday -> {
                        WeekdayCell(
                            weekday = item.day,
                            modifier = Modifier.aspectRatio(1f)
                        )
                    }

                    CalendarItem.Empty -> {
                        Spacer(
                            modifier = Modifier.aspectRatio(1f)
                        )
                    }

                    is CalendarItem.Day -> {
                        CalendarCell(
                            textDate = item.date,
                            signal = item.signal,
                            modifier = Modifier,
                            onClick = {onClick(item.date)}
                        )
                    }
                }
            }
        }
    }
}


//Calendar cell shown on the calendar window
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarCell(
    textDate: Date,
    signal: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Date) -> Unit
) {

    val today = LocalDate.now()

    val localDate = textDate.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val isToday = (today == localDate)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick(textDate) }
    ) {

        if (signal) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(4.dp)
                    .background(
                        color = if(isToday){MaterialTheme.colorScheme.secondary}
                                else {MaterialTheme.colorScheme.tertiary},
                        shape = if (isToday) { CutCornerShape(12.dp)}
                                else {CircleShape}
                    )
            )
        }

        Text(
            text = textDate.formatToCalendarDate(),
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary
        )
    }
}


//Returns the top banner an addition floating button int he bottom right corner
@Composable
fun BannerFormatAndFloatingButtons(
        username : String,
        date: String,
        profileOnClick : () -> Unit,
        list: List<NavigationOptions>,
        showNav: Boolean,
        navOnClick: () -> Unit,
        onDismissNav: () -> Unit,
        floatingAddOnClick: () -> Unit,
        content: @Composable (PaddingValues) -> Unit
){
    Scaffold(
        topBar = {
            TopBanner(
                username,
                date,
                list,
                showNav,
                onDismissNav,
                navOnClick,
                profileOnClick
            )
        },
        floatingActionButton = {
            IconButton(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .size(80.dp)
                    .padding(20.dp),
                onClick = floatingAddOnClick) {
                Icon(
                    modifier = Modifier
                        .size(60.dp)
                        .testTag("staticButton"),
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Menu",
                    tint = MaterialTheme.colorScheme.background)
            }
        }
    ) {
            padding -> content(padding)
    }
}
