<div>
    <h3>Add Periodical</h3>
    <form action="/admin/new-periodical" method="POST">
        <table>
            <tbody>
            <tr>
                <td><label>Name:</label></td>
                <td><input type="text" name="name"></td>
            </tr>
            <tr>
                <td><label>Price:</label></td>
                <td><input type="number" name="price" step=".01"></td>
            </tr>

            <tr>
                <td><label>Description:</label></td>
                <td><input type="text" name="description"></td>
            </tr>

            <tr>
                <td><label></label></td>
                <td><input type="submit" value="Save"></td>
            </tr>
            </tbody>
        </table>
    </form>

    <a href="/admin/periodicals">Back to List</a>
</div>